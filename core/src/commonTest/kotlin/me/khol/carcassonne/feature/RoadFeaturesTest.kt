package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basic.V
import kotlin.test.Test
import kotlin.test.assertEquals

class RoadFeaturesTest {

    @Test
    fun `road features can merge`() {
        val board = Board.starting(startingTile = Tiles.Basic.W)
        assertEquals(
            expected = setOf(
                Feature.Road(placedRoads = setOf(PlacedRoad(Coordinates(0, 0), Element.Road { left })), isFinished = false, figures = emptyList()),
                Feature.Road(placedRoads = setOf(PlacedRoad(Coordinates(0, 0), Element.Road { right })), isFinished = false, figures = emptyList()),
                Feature.Road(placedRoads = setOf(PlacedRoad(Coordinates(0, 0), Element.Road { bottom })), isFinished = false, figures = emptyList()),
            ),
            actual = board.getRoadFeatures(),
        )

        // Connect right and bottom road ends with three turns
        val newBoard = board
            .placeTile(Coordinates(x = 1, y = 0), RotatedTile(Tiles.Basic.V, Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(x = 1, y = 1), RotatedTile(Tiles.Basic.V, Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(x = 0, y = 1), RotatedTile(Tiles.Basic.V, Rotation.ROTATE_180), emptyList())

        // The two ends now form a loop and are a single feature
        assertEquals(
            expected = setOf(
                Feature.Road(
                    placedRoads = setOf(
                        PlacedRoad(Coordinates(0, 0), Element.Road { left }),
                    ),
                    isFinished = false,
                    figures = emptyList(),
                ),
                Feature.Road(
                    placedRoads = setOf(
                        PlacedRoad(Coordinates(0, 0), Element.Road { right }),
                        PlacedRoad(Coordinates(1, 0), Element.Road { left + bottom }),
                        PlacedRoad(Coordinates(1, 1), Element.Road { top + left }),
                        PlacedRoad(Coordinates(0, 1), Element.Road { right + top }),
                        PlacedRoad(Coordinates(0, 0), Element.Road { bottom }),
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
        val figureOne = PlacedFigure(
            placedElement = PlacedElement(
                coordinates = Coordinates(1, 0),
                element = V.road.rotate(Rotation.ROTATE_0),
            ),
            figure = PlayerFigures.greenMeeple,
        )
        val figureTwo = PlacedFigure(
            placedElement = PlacedElement(
                coordinates = Coordinates(0, 1),
                element = V.road.rotate(Rotation.ROTATE_180),
            ),
            figure = PlayerFigures.greenMeeple,
        )

        val board = Board.starting(startingTile = Tiles.Basic.W)
            .placeTile(Coordinates(x = 1, y = 0), RotatedTile(Tiles.Basic.V, Rotation.ROTATE_0), listOf(figureOne))
            .placeTile(Coordinates(x = 0, y = 1), RotatedTile(Tiles.Basic.V, Rotation.ROTATE_180), listOf(figureTwo))
            .placeTile(Coordinates(x = 1, y = 1), RotatedTile(Tiles.Basic.V, Rotation.ROTATE_90), emptyList())

        assertEquals(
            expected = setOf(
                emptySet(),
                setOf(figureOne, figureTwo),
            ),
            actual = board.getRoadFeatures().map { it.figures.toSet() }.toSet(),
        )
    }
}