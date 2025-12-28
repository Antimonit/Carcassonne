package me.khol.carcassonne

import me.khol.carcassonne.feature.PlacedElement
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.fixtures.Players
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basic.D
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidFigurePlacementsTest {

    @Test
    fun `valid figure placements`() {
        val board = Board.starting(startingTile = Tiles.Basic.D)
            .placeTile(
                coordinates = Coordinates(1, 0),
                tile = Tiles.Basic.D.rotated(rotation = Rotation.ROTATE_180),
                placedFigures = listOf(
                    PlacedFigure(
                        placedElement = PlacedElement(
                            coordinates = Coordinates(1, 0),
                            element = D.fieldTop.rotate(Rotation.ROTATE_180),
                        ),
                        figure = PlayerFigures.greenMeeple,
                    ),
                ),
            )

        val placements = board.validFigurePlacements(
            placedTile = PlacedTile(
                rotatedTile = Tiles.Basic.D.rotated(rotation = Rotation.ROTATE_180),
                coordinates = Coordinates(-1, 0),
            ),
            currentPlayer = Players.green,
        )

        assertEquals(
            expected = mapOf(
                D.fieldTop.rotate(Rotation.ROTATE_180) to emptyList(),
                D.fieldBottom.rotate(Rotation.ROTATE_180) to listOf(
                    PlacedFigure(
                        placedElement = PlacedElement(
                            coordinates = Coordinates(-1, 0),
                            element = D.fieldBottom.rotate(Rotation.ROTATE_180),
                        ),
                        figure = PlayerFigures.greenMeeple,
                    ),
                ),
                D.road.rotate(Rotation.ROTATE_180) to listOf(
                    PlacedFigure(
                        placedElement = PlacedElement(
                            coordinates = Coordinates(-1, 0),
                            element = D.road.rotate(Rotation.ROTATE_180),
                        ),
                        figure = PlayerFigures.greenMeeple,
                    ),
                ),
                D.city.rotate(Rotation.ROTATE_180) to listOf(
                    PlacedFigure(
                        placedElement = PlacedElement(
                            coordinates = Coordinates(-1, 0),
                            element = D.city.rotate(Rotation.ROTATE_180),
                        ),
                        figure = PlayerFigures.greenMeeple,
                    ),
                ),
            ),
            actual = placements,
        )
    }
}
