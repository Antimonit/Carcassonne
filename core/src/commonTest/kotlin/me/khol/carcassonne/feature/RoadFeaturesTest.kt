package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.basic.V
import me.khol.carcassonne.tiles.basic.W
import kotlin.test.Test
import kotlin.test.assertEquals

class RoadFeaturesTest {

    @Test
    fun `road features can merge`() {
        val board = Board.Companion.starting(startingTile = W)
        assertEquals(
            expected = setOf(
                Feature.Road(roads = setOf(PlacedRoadGroup(Coordinates(0, 0), ElementGroup.Road { left })), isFinished = false),
                Feature.Road(roads = setOf(PlacedRoadGroup(Coordinates(0, 0), ElementGroup.Road { right })), isFinished = false),
                Feature.Road(roads = setOf(PlacedRoadGroup(Coordinates(0, 0), ElementGroup.Road { bottom })), isFinished = false),
            ),
            actual = board.getRoadFeatures(),
        )

        // Connect right and bottom road ends with three turns
        val newBoard = board
            .placeTile(Coordinates(x = 1, y = 0), RotatedTile(V, Rotation.ROTATE_0))
            .placeTile(Coordinates(x = 1, y = 1), RotatedTile(V, Rotation.ROTATE_90))
            .placeTile(Coordinates(x = 0, y = 1), RotatedTile(V, Rotation.ROTATE_180))

        // The two ends now form a loop and are a single feature
        assertEquals(
            expected = setOf(
                Feature.Road(
                    roads = setOf(
                        PlacedRoadGroup(Coordinates(0, 0), ElementGroup.Road { left }),
                    ),
                    isFinished = false,
                ),
                Feature.Road(
                    roads = setOf(
                        PlacedRoadGroup(Coordinates(0, 0), ElementGroup.Road { right }),
                        PlacedRoadGroup(Coordinates(1, 0), ElementGroup.Road { left + bottom }),
                        PlacedRoadGroup(Coordinates(1, 1), ElementGroup.Road { top + left }),
                        PlacedRoadGroup(Coordinates(0, 1), ElementGroup.Road { right + top }),
                        PlacedRoadGroup(Coordinates(0, 0), ElementGroup.Road { bottom }),
                    ),
                    isFinished = true,
                ),
            ),
            actual = newBoard.getRoadFeatures(),
        )
    }
}