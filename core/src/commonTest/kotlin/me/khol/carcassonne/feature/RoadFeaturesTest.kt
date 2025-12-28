package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.fixtures.PlayerFigures
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
                Feature.Road(placedRoads = setOf(PlacedRoad(Coordinates(0, 0), w.roadLeft.rotate(Rotation.ROTATE_0))), isFinished = false, figures = emptyList()),
                Feature.Road(placedRoads = setOf(PlacedRoad(Coordinates(0, 0), w.roadRight.rotate(Rotation.ROTATE_0))), isFinished = false, figures = emptyList()),
                Feature.Road(placedRoads = setOf(PlacedRoad(Coordinates(0, 0), w.roadBottom.rotate(Rotation.ROTATE_0))), isFinished = false, figures = emptyList()),
            ),
            actual = board.getRoadFeatures(),
        )

        // Connect right and bottom road ends with three turns
        val newBoard = board
            .placeTile(Coordinates(x = 1, y = 0), v.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(x = 1, y = 1), v.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(x = 0, y = 1), v.tile.rotated(Rotation.ROTATE_180), emptyList())

        // The two ends now form a loop and are a single feature
        assertEquals(
            expected = setOf(
                Feature.Road(
                    placedRoads = setOf(
                        PlacedRoad(Coordinates(0, 0), w.roadLeft.rotate(Rotation.ROTATE_0)),
                    ),
                    isFinished = false,
                    figures = emptyList(),
                ),
                Feature.Road(
                    placedRoads = setOf(
                        PlacedRoad(Coordinates(0, 0), w.roadRight.rotate(Rotation.ROTATE_0)),
                        PlacedRoad(Coordinates(1, 0), v.road.rotate(Rotation.ROTATE_0)),
                        PlacedRoad(Coordinates(1, 1), v.road.rotate(Rotation.ROTATE_90)),
                        PlacedRoad(Coordinates(0, 1), v.road.rotate(Rotation.ROTATE_180)),
                        PlacedRoad(Coordinates(0, 0), w.roadBottom.rotate(Rotation.ROTATE_0)),
                    ),
                    isFinished = true,
                    figures = emptyList(),
                ),
            ),
            actual = newBoard.getRoadFeatures(),
        )
    }

    @Test
    fun `road features with figures`() {
        val v = me.khol.carcassonne.tiles.basic.V
        val w = me.khol.carcassonne.tiles.basic.W
        val figureOne = PlacedFigure(
            placedElement = v.road.rotate(Rotation.ROTATE_0).placed(Coordinates(1, 0)),
            figure = PlayerFigures.greenMeeple,
        )
        val figureTwo = PlacedFigure(
            placedElement = v.road.rotate(Rotation.ROTATE_180).placed(Coordinates(0, 1)),
            figure = PlayerFigures.greenMeeple,
        )

        val board = Board.starting(startingTile = w.tile)
            .placeTile(Coordinates(x = 1, y = 0), v.tile.rotated(Rotation.ROTATE_0), listOf(figureOne))
            .placeTile(Coordinates(x = 0, y = 1), v.tile.rotated(Rotation.ROTATE_180), listOf(figureTwo))
            .placeTile(Coordinates(x = 1, y = 1), v.tile.rotated(Rotation.ROTATE_90), emptyList())

        assertEquals(
            expected = setOf(
                emptySet(),
                setOf(figureOne, figureTwo),
            ),
            actual = board.getRoadFeatures().map { it.figures.toSet() }.toSet(),
        )
    }
}