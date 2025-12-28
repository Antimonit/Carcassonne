package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldFeaturesTest {

    @Test
    fun `basic field feature`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)

        assertEquals(
            expected = setOf(
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Tiles.Basic.D.fieldBottom.rotate(Rotation.ROTATE_0),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = d.fieldTop.rotate(Rotation.ROTATE_0),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
            ),
            actual = board.getFieldFeatures(),
        )
    }

    @Test
    fun `field features with completed cities`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)

        val newBoard = board
            .placeTile(Coordinates(0, -1), Tiles.Basic.H.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(0, -2), Tiles.Basic.E.tile.rotated(Rotation.ROTATE_180), emptyList())

        val bottomCity = Feature.City(
            placedCities = setOf(
                PlacedCity(Coordinates(0, 0), element = Tiles.Basic.D.city.rotate(Rotation.ROTATE_0)),
                PlacedCity(Coordinates(0, -1), element = Tiles.Basic.H.cityBottom.rotate(Rotation.ROTATE_0)),
            ),
            isFinished = true,
            figures = emptyList(),
        )
        val topCity = Feature.City(
            placedCities = setOf(
                PlacedCity(Coordinates(0, -1), element = Tiles.Basic.H.cityTop.rotate(Rotation.ROTATE_0)),
                PlacedCity(Coordinates(0, -2), element = Tiles.Basic.E.city.rotate(Rotation.ROTATE_180)),
            ),
            isFinished = true,
            figures = emptyList(),
        )

        assertEquals(
            expected = setOf(
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(x = 0, y = 0),
                            element = Tiles.Basic.D.fieldBottom.rotate(Rotation.ROTATE_0),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(x = 0, y = 0),
                            element = Tiles.Basic.D.fieldTop.rotate(Rotation.ROTATE_0),
                        ),
                    ),
                    connectedCities = setOf(bottomCity),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(x = 0, y = -1),
                            element = Tiles.Basic.H.field.rotate(Rotation.ROTATE_0),
                        ),
                    ),
                    connectedCities = setOf(bottomCity, topCity),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(x = 0, y = -2),
                            element = Tiles.Basic.E.field.rotate(Rotation.ROTATE_180),
                        ),
                    ),
                    connectedCities = setOf(topCity),
                    figures = emptyList(),
                ),
            ),
            actual = newBoard.getFieldFeatures(),
        )
    }

    @Test
    fun `field features can merge`() {
        val board = Board.starting(startingTile = Tiles.Basic.L.tile)
            .placeTile(Coordinates(0, 1), Tiles.Basic.K.tile.rotated(Rotation.ROTATE_180), emptyList())

        assertEquals(
            expected = setOf(
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Tiles.Basic.L.fieldTop.rotate(Rotation.ROTATE_0),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Tiles.Basic.L.fieldLeft.rotate(Rotation.ROTATE_0),
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 1),
                            element = Tiles.Basic.K.fieldTop.rotate(Rotation.ROTATE_180),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Tiles.Basic.L.fieldRight.rotate(Rotation.ROTATE_0),
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 1),
                            element = Tiles.Basic.K.fieldBottom.rotate(Rotation.ROTATE_180),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
            ),
            actual = board.getFieldFeatures(),
        )

        val newBoard = board
            .placeTile(Coordinates(-1, 0), Tiles.Basic.A.tile.rotated(Rotation.ROTATE_270), emptyList())

        assertEquals(
            expected = setOf(
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Tiles.Basic.L.fieldTop.rotate(Rotation.ROTATE_0),
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Tiles.Basic.L.fieldLeft.rotate(Rotation.ROTATE_0),
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 1),
                            element = Tiles.Basic.K.fieldTop.rotate(Rotation.ROTATE_180),
                        ),
                        PlacedField(
                            coordinates = Coordinates(-1, 0),
                            element = Tiles.Basic.A.field.rotate(Rotation.ROTATE_270),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Tiles.Basic.L.fieldRight.rotate(Rotation.ROTATE_0),
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 1),
                            element = Tiles.Basic.K.fieldBottom.rotate(Rotation.ROTATE_180),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
            ),
            actual = newBoard.getFieldFeatures(),
        )
    }

    @Test
    fun `field features with figures`() {
        val figureOne = PlacedFigure(
            placedElement = Tiles.Basic.E.field.rotate(Rotation.ROTATE_180).placed(Coordinates(0, -1)),
            figure = PlayerFigures.greenMeeple,
        )
        val figureTwo = PlacedFigure(
            placedElement = Tiles.Basic.E.field.rotate(Rotation.ROTATE_180).placed(Coordinates(1, 0)),
            figure = PlayerFigures.greenMeeple,
        )

        val board = Board.starting(startingTile = Tiles.Basic.E.tile)
            .placeTile(Coordinates(0, -1), Tiles.Basic.E.tile.rotated(Rotation.ROTATE_180), listOf(figureOne))
            .placeTile(Coordinates(1, 0), Tiles.Basic.E.tile.rotated(Rotation.ROTATE_180), listOf(figureTwo))
            .placeTile(Coordinates(1, -1), Tiles.Basic.E.tile.rotated(Rotation.ROTATE_0), emptyList())

        assertEquals(
            expected = setOf(
                setOf(figureOne, figureTwo)
            ),
            actual = board.getFieldFeatures().map { it.figures.toSet() }.toSet(),
        )
    }
}
