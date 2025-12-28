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
        val d = me.khol.carcassonne.tiles.basic.D
        val board = Board.starting(startingTile = d.tile)

        assertEquals(
            expected = setOf(
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = d.fieldBottom.rotate(Rotation.ROTATE_0),
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
        val d = me.khol.carcassonne.tiles.basic.D
        val e = me.khol.carcassonne.tiles.basic.E
        val h = me.khol.carcassonne.tiles.basic.H
        val board = Board.starting(startingTile = Tiles.Basic.D)

        val newBoard = board
            .placeTile(Coordinates(0, -1), h.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(0, -2), e.tile.rotated(Rotation.ROTATE_180), emptyList())

        val bottomCity = Feature.City(
            placedCities = setOf(
                PlacedCity(Coordinates(0, 0), element = d.city.rotate(Rotation.ROTATE_0)),
                PlacedCity(Coordinates(0, -1), element = h.cityBottom.rotate(Rotation.ROTATE_0)),
            ),
            isFinished = true,
            figures = emptyList(),
        )
        val topCity = Feature.City(
            placedCities = setOf(
                PlacedCity(Coordinates(0, -1), element = h.cityTop.rotate(Rotation.ROTATE_0)),
                PlacedCity(Coordinates(0, -2), element = e.city.rotate(Rotation.ROTATE_180)),
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
                            element = d.fieldBottom.rotate(Rotation.ROTATE_0),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(x = 0, y = 0),
                            element = d.fieldTop.rotate(Rotation.ROTATE_0),
                        ),
                    ),
                    connectedCities = setOf(bottomCity),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(x = 0, y = -1),
                            element = h.field.rotate(Rotation.ROTATE_0),
                        ),
                    ),
                    connectedCities = setOf(bottomCity, topCity),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(x = 0, y = -2),
                            element = e.field.rotate(Rotation.ROTATE_180),
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
        val a = me.khol.carcassonne.tiles.basic.A
        val l = me.khol.carcassonne.tiles.basic.L
        val k = me.khol.carcassonne.tiles.basic.K
        val board = Board.starting(startingTile = l.tile)
            .placeTile(Coordinates(0, 1), k.tile.rotated(Rotation.ROTATE_180), emptyList())

        assertEquals(
            expected = setOf(
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = l.fieldTop.rotate(Rotation.ROTATE_0),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = l.fieldLeft.rotate(Rotation.ROTATE_0),
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 1),
                            element = k.fieldTop.rotate(Rotation.ROTATE_180),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = l.fieldRight.rotate(Rotation.ROTATE_0),
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 1),
                            element = k.fieldBottom.rotate(Rotation.ROTATE_180),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
            ),
            actual = board.getFieldFeatures(),
        )

        val newBoard = board
            .placeTile(Coordinates(-1, 0), Tiles.Basic.A.rotated(Rotation.ROTATE_270), emptyList())

        assertEquals(
            expected = setOf(
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = l.fieldTop.rotate(Rotation.ROTATE_0),
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = l.fieldLeft.rotate(Rotation.ROTATE_0),
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 1),
                            element = k.fieldTop.rotate(Rotation.ROTATE_180),
                        ),
                        PlacedField(
                            coordinates = Coordinates(-1, 0),
                            element = a.field.rotate(Rotation.ROTATE_270),
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = l.fieldRight.rotate(Rotation.ROTATE_0),
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 1),
                            element = k.fieldBottom.rotate(Rotation.ROTATE_180),
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
        val e = me.khol.carcassonne.tiles.basic.E
        val figureOne = PlacedFigure(
            placedElement = e.field.rotate(Rotation.ROTATE_180).placed(Coordinates(0, -1)),
            figure = PlayerFigures.greenMeeple,
        )
        val figureTwo = PlacedFigure(
            placedElement = e.field.rotate(Rotation.ROTATE_180).placed(Coordinates(1, 0)),
            figure = PlayerFigures.greenMeeple,
        )

        val board = Board.starting(startingTile = e.tile)
            .placeTile(Coordinates(0, -1), e.tile.rotated(Rotation.ROTATE_180), listOf(figureOne))
            .placeTile(Coordinates(1, 0), e.tile.rotated(Rotation.ROTATE_180), listOf(figureTwo))
            .placeTile(Coordinates(1, -1), e.tile.rotated(Rotation.ROTATE_0), emptyList())

        assertEquals(
            expected = setOf(
                setOf(figureOne, figureTwo)
            ),
            actual = board.getFieldFeatures().map { it.figures.toSet() }.toSet(),
        )
    }
}
