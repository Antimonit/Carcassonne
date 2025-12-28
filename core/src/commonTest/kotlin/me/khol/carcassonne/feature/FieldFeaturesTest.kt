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
                            rotatedElement = Tiles.Basic.D.fieldBottom.rotated(Rotation.ROTATE_0),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            rotatedElement = Tiles.Basic.D.fieldTop.rotated(Rotation.ROTATE_0),
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
                PlacedCity(Coordinates(0, 0), rotatedElement = Tiles.Basic.D.city.rotated(Rotation.ROTATE_0)),
                PlacedCity(Coordinates(0, -1), rotatedElement = Tiles.Basic.H.cityBottom.rotated(Rotation.ROTATE_0)),
            ),
            isFinished = true,
            figures = emptyList(),
        )
        val topCity = Feature.City(
            placedCities = setOf(
                PlacedCity(Coordinates(0, -1), rotatedElement = Tiles.Basic.H.cityTop.rotated(Rotation.ROTATE_0)),
                PlacedCity(Coordinates(0, -2), rotatedElement = Tiles.Basic.E.city.rotated(Rotation.ROTATE_180)),
            ),
            isFinished = true,
            figures = emptyList(),
        )

        assertEquals(
            expected = setOf(
                Feature.Field(
                    placedFields = setOf(
                        Tiles.Basic.D.fieldBottom.rotated(Rotation.ROTATE_0).placed(Coordinates(x = 0, y = 0)),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        Tiles.Basic.D.fieldTop.rotated(Rotation.ROTATE_0).placed(Coordinates(x = 0, y = 0)),
                    ),
                    connectedCities = setOf(bottomCity),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        Tiles.Basic.H.field.rotated(Rotation.ROTATE_0).placed(Coordinates(x = 0, y = -1)),
                    ),
                    connectedCities = setOf(bottomCity, topCity),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        Tiles.Basic.E.field.rotated(Rotation.ROTATE_180).placed(Coordinates(x = 0, y = -2)),
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
                        Tiles.Basic.L.fieldTop.rotated(Rotation.ROTATE_0).placed(Coordinates(0, 0)),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        Tiles.Basic.L.fieldLeft.rotated(Rotation.ROTATE_0).placed(Coordinates(0, 0)),
                        Tiles.Basic.K.fieldTop.rotated(Rotation.ROTATE_180).placed(Coordinates(0, 1)),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        Tiles.Basic.L.fieldRight.rotated(Rotation.ROTATE_0).placed(Coordinates(0, 0)),
                        Tiles.Basic.K.fieldBottom.rotated(Rotation.ROTATE_180).placed(Coordinates(0, 1)),
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
                        Tiles.Basic.L.fieldTop.rotated(Rotation.ROTATE_0).placed(Coordinates(0, 0)),
                        Tiles.Basic.L.fieldLeft.rotated(Rotation.ROTATE_0).placed(Coordinates(0, 0)),
                        Tiles.Basic.K.fieldTop.rotated(Rotation.ROTATE_180).placed(Coordinates(0, 1)),
                        Tiles.Basic.A.field.rotated(Rotation.ROTATE_270).placed(Coordinates(-1, 0)),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        Tiles.Basic.L.fieldRight.rotated(Rotation.ROTATE_0).placed(Coordinates(0, 0)),
                        Tiles.Basic.K.fieldBottom.rotated(Rotation.ROTATE_180).placed(Coordinates(0, 1)),
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
            placedElement = Tiles.Basic.E.field.rotated(Rotation.ROTATE_180).placed(Coordinates(0, -1)),
            figure = PlayerFigures.greenMeeple,
        )
        val figureTwo = PlacedFigure(
            placedElement = Tiles.Basic.E.field.rotated(Rotation.ROTATE_180).placed(Coordinates(1, 0)),
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
