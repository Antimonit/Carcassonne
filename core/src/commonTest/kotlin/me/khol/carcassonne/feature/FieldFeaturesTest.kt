package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldFeaturesTest {

    @Test
    fun `basic field feature`() {
        val board = Board.starting(startingTile = Tiles.Basic.D)

        assertEquals(
            expected = setOf(
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Element.Field { bottom + leftBottom + rightBottom },
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Element.Field(
                                Element.City { top },
                            ) { leftTop + rightTop },
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
        val board = Board.starting(startingTile = Tiles.Basic.D)

        val newBoard = board
            .placeTile(Coordinates(0, -1), RotatedTile(Tiles.Basic.H, Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(0, -2), RotatedTile(Tiles.Basic.E, Rotation.ROTATE_180), emptyList())

        val bottomCity = Feature.City(
            placedCities = setOf(
                PlacedCity(Coordinates(0, 0), element = Element.City { top }),
                PlacedCity(Coordinates(0, -1), element = Element.City { bottom }),
            ),
            isFinished = true,
            figures = emptyList(),
        )
        val topCity = Feature.City(
            placedCities = setOf(
                PlacedCity(Coordinates(0, -1), element = Element.City { top }),
                PlacedCity(Coordinates(0, -2), element = Element.City { bottom }),
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
                            element = Element.Field { bottom + leftBottom + rightBottom },
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(x = 0, y = 0),
                            element = Element.Field(
                                Element.City { top },
                            ) { leftTop + rightTop },
                        ),
                    ),
                    connectedCities = setOf(bottomCity),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(x = 0, y = -1),
                            element = Element.Field(
                                Element.City { bottom },
                                Element.City { top },
                            ) { left + right },
                        ),
                    ),
                    connectedCities = setOf(bottomCity, topCity),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(x = 0, y = -2),
                            element = Element.Field(
                                Element.City { bottom },
                            ) { left + right + top }),
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
        val board = Board.starting(startingTile = Tiles.Basic.L)
            .placeTile(Coordinates(0, 1), RotatedTile(Tiles.Basic.K, Rotation.ROTATE_180), emptyList())

        assertEquals(
            expected = setOf(
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Element.Field(Element.City { top }) { leftTop + rightTop },
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Element.Field { leftBottom + bottomLeft },
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 1),
                            element = Element.Field(Element.City { bottom }) { left + topLeft + rightBottom },
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Element.Field { rightBottom + bottomRight },
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 1),
                            element = Element.Field { rightTop + topRight },
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
            ),
            actual = board.getFieldFeatures(),
        )

        val newBoard = board
            .placeTile(Coordinates(-1, 0), RotatedTile(Tiles.Basic.A, Rotation.ROTATE_270), emptyList())

        assertEquals(
            expected = setOf(
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Element.Field(Element.City { top }) { leftTop + rightTop },
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Element.Field { leftBottom + bottomLeft },
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 1),
                            element = Element.Field(Element.City { bottom }) { left + topLeft + rightBottom },
                        ),
                        PlacedField(
                            coordinates = Coordinates(-1, 0),
                            element = Element.Field { all },
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
                Feature.Field(
                    placedFields = setOf(
                        PlacedField(
                            coordinates = Coordinates(0, 0),
                            element = Element.Field { rightBottom + bottomRight },
                        ),
                        PlacedField(
                            coordinates = Coordinates(0, 1),
                            element = Element.Field { rightTop + topRight },
                        ),
                    ),
                    connectedCities = emptySet(),
                    figures = emptyList(),
                ),
            ),
            actual = newBoard.getFieldFeatures(),
        )
    }
}
