package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.placed
import me.khol.carcassonne.rotated
import kotlin.test.Test
import kotlin.test.assertEquals

class RoadFeaturesTest {

    @Test
    fun `road features can merge`() {
        val v = me.khol.carcassonne.tiles.basic.V
        val w = me.khol.carcassonne.tiles.basic.W
        val board = Board.starting(startingTile = w.tile)
        assertEquals(
            expected = setOf(
                Road(placedRoads = setOf(w.roadLeft.rotated(Rotation.ROTATE_0).placed(0, 0)), isFinished = false, figures = emptyList()),
                Road(placedRoads = setOf(w.roadRight.rotated(Rotation.ROTATE_0).placed(0, 0)), isFinished = false, figures = emptyList()),
                Road(placedRoads = setOf(w.roadBottom.rotated(Rotation.ROTATE_0).placed(0, 0)), isFinished = false, figures = emptyList()),
            ),
            actual = board.roadFeatures,
        )

        // Connect right and bottom road ends with three turns
        val newBoard = board
            .placeTile(v.tile.rotated(Rotation.ROTATE_0).placed(1, 0), emptyList())
            .placeTile(v.tile.rotated(Rotation.ROTATE_90).placed(1, 1), emptyList())
            .placeTile(v.tile.rotated(Rotation.ROTATE_180).placed(0, 1), emptyList())

        // The two ends now form a loop and are a single feature
        assertEquals(
            expected = setOf(
                Road(
                    placedRoads = setOf(
                        w.roadLeft.rotated(Rotation.ROTATE_0).placed(0, 0),
                    ),
                    isFinished = false,
                    figures = emptyList(),
                ),
                Road(
                    placedRoads = setOf(
                        w.roadRight.rotated(Rotation.ROTATE_0).placed(0, 0),
                        v.road.rotated(Rotation.ROTATE_0).placed(1, 0),
                        v.road.rotated(Rotation.ROTATE_90).placed(1, 1),
                        v.road.rotated(Rotation.ROTATE_180).placed(0, 1),
                        w.roadBottom.rotated(Rotation.ROTATE_0).placed(0, 0),
                    ),
                    isFinished = true,
                    figures = emptyList(),
                ),
            ),
            actual = newBoard.roadFeatures,
        )
    }

    @Test
    fun `road features with figures`() {
        val v = me.khol.carcassonne.tiles.basic.V
        val w = me.khol.carcassonne.tiles.basic.W
        val figureOne = PlacedFigure(
            placedElement = v.road.rotated(Rotation.ROTATE_0).placed(1, 0),
            figure = PlayerFigures.greenMeeple,
        )
        val figureTwo = PlacedFigure(
            placedElement = v.road.rotated(Rotation.ROTATE_180).placed(0, 1),
            figure = PlayerFigures.greenMeeple,
        )

        val board = Board.starting(startingTile = w.tile)
            .placeTile(v.tile.rotated(Rotation.ROTATE_0).placed(1, 0), listOf(figureOne))
            .placeTile(v.tile.rotated(Rotation.ROTATE_180).placed(0, 1), listOf(figureTwo))
            .placeTile(v.tile.rotated(Rotation.ROTATE_90).placed(1, 1), emptyList())

        assertEquals(
            expected = setOf(
                emptySet(),
                setOf(figureOne, figureTwo),
            ),
            actual = board.roadFeatures.map { it.figures.toSet() }.toSet(),
        )
    }
}