package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.basic.A
import me.khol.carcassonne.tiles.basic.D
import me.khol.carcassonne.tiles.basic.E
import me.khol.carcassonne.tiles.basic.H
import me.khol.carcassonne.tiles.basic.K
import me.khol.carcassonne.tiles.basic.L
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldFeaturesTest {

    @Test
    fun `basic field feature`() {
        val board = Board.starting(startingTile = D)

        assertEquals(
            expected = setOf(
                Feature.Field(
                    fields = setOf(
                        PlacedFieldGroup(
                            coordinates = Coordinates(0, 0),
                            elementGroup = ElementGroup.Field { bottom + leftBottom + rightBottom },
                        ),
                    ),
                    connectedCities = emptySet(),
                ),
                Feature.Field(
                    fields = setOf(
                        PlacedFieldGroup(
                            coordinates = Coordinates(0, 0),
                            elementGroup = ElementGroup.Field(
                                ElementGroup.City { top },
                            ) { leftTop + rightTop },
                        ),
                    ),
                    connectedCities = emptySet(),
                ),
            ),
            actual = board.getFieldFeatures(),
        )
    }

    @Test
    fun `field features with completed cities`() {
        val board = Board.starting(startingTile = D)

        val newBoard = board
            .placeTile(Coordinates(0, -1), RotatedTile(H, Rotation.ROTATE_0))
            .placeTile(Coordinates(0, -2), RotatedTile(E, Rotation.ROTATE_180))

        val bottomCity = Feature.City(
            cities = setOf(
                PlacedCityGroup(Coordinates(0, 0), elementGroup = ElementGroup.City { top }),
                PlacedCityGroup(Coordinates(0, -1), elementGroup = ElementGroup.City { bottom }),
            ),
            isFinished = true,
        )
        val topCity = Feature.City(
            cities = setOf(
                PlacedCityGroup(Coordinates(0, -1), elementGroup = ElementGroup.City { top }),
                PlacedCityGroup(Coordinates(0, -2), elementGroup = ElementGroup.City { bottom }),
            ),
            isFinished = true,
        )

        assertEquals(
            expected = setOf(
                Feature.Field(
                    fields = setOf(
                        PlacedFieldGroup(
                            coordinates = Coordinates(x = 0, y = 0),
                            elementGroup = ElementGroup.Field { bottom + leftBottom + rightBottom },
                        ),
                    ),
                    connectedCities = emptySet(),
                ),
                Feature.Field(
                    fields = setOf(
                        PlacedFieldGroup(
                            coordinates = Coordinates(x = 0, y = 0),
                            elementGroup = ElementGroup.Field(
                                ElementGroup.City { top },
                            ) { leftTop + rightTop },
                        ),
                    ),
                    connectedCities = setOf(bottomCity),
                ),
                Feature.Field(
                    fields = setOf(
                        PlacedFieldGroup(
                            coordinates = Coordinates(x = 0, y = -1),
                            elementGroup = ElementGroup.Field(
                                ElementGroup.City { bottom },
                                ElementGroup.City { top },
                            ) { left + right },
                        ),
                    ),
                    connectedCities = setOf(bottomCity, topCity),
                ),
                Feature.Field(
                    fields = setOf(
                        PlacedFieldGroup(
                            coordinates = Coordinates(x = 0, y = -2),
                            elementGroup = ElementGroup.Field(
                                ElementGroup.City { bottom },
                            ) { left + right + top }),
                    ),
                    connectedCities = setOf(topCity),
                ),
            ),
            actual = newBoard.getFieldFeatures(),
        )
    }

    @Test
    fun `field features can merge`() {
        val board = Board.starting(startingTile = L)
            .placeTile(Coordinates(0, 1), RotatedTile(K, Rotation.ROTATE_180))

        assertEquals(
            expected = setOf(
                Feature.Field(
                    fields = setOf(
                        PlacedFieldGroup(
                            coordinates = Coordinates(0, 0),
                            elementGroup = ElementGroup.Field(ElementGroup.City { top }) { leftTop + rightTop },
                        ),
                    ),
                    connectedCities = emptySet(),
                ),
                Feature.Field(
                    fields = setOf(
                        PlacedFieldGroup(
                            coordinates = Coordinates(0, 0),
                            elementGroup = ElementGroup.Field { leftBottom + bottomLeft },
                        ),
                        PlacedFieldGroup(
                            coordinates = Coordinates(0, 1),
                            elementGroup = ElementGroup.Field(ElementGroup.City { bottom }) { left + topLeft + rightBottom },
                        ),
                    ),
                    connectedCities = emptySet(),
                ),
                Feature.Field(
                    fields = setOf(
                        PlacedFieldGroup(
                            coordinates = Coordinates(0, 0),
                            elementGroup = ElementGroup.Field { rightBottom + bottomRight },
                        ),
                        PlacedFieldGroup(
                            coordinates = Coordinates(0, 1),
                            elementGroup = ElementGroup.Field { rightTop + topRight },
                        ),
                    ),
                    connectedCities = emptySet(),
                ),
            ),
            actual = board.getFieldFeatures(),
        )

        val newBoard = board
            .placeTile(Coordinates(-1, 0), RotatedTile(A, Rotation.ROTATE_270))

        assertEquals(
            expected = setOf(
                Feature.Field(
                    fields = setOf(
                        PlacedFieldGroup(
                            coordinates = Coordinates(0, 0),
                            elementGroup = ElementGroup.Field(ElementGroup.City { top }) { leftTop + rightTop },
                        ),
                        PlacedFieldGroup(
                            coordinates = Coordinates(0, 0),
                            elementGroup = ElementGroup.Field { leftBottom + bottomLeft },
                        ),
                        PlacedFieldGroup(
                            coordinates = Coordinates(0, 1),
                            elementGroup = ElementGroup.Field(ElementGroup.City { bottom }) { left + topLeft + rightBottom },
                        ),
                        PlacedFieldGroup(
                            coordinates = Coordinates(-1, 0),
                            elementGroup = ElementGroup.Field { all },
                        ),
                    ),
                    connectedCities = emptySet(),
                ),
                Feature.Field(
                    fields = setOf(
                        PlacedFieldGroup(
                            coordinates = Coordinates(0, 0),
                            elementGroup = ElementGroup.Field { rightBottom + bottomRight },
                        ),
                        PlacedFieldGroup(
                            coordinates = Coordinates(0, 1),
                            elementGroup = ElementGroup.Field { rightTop + topRight },
                        ),
                    ),
                    connectedCities = emptySet(),
                ),
            ),
            actual = newBoard.getFieldFeatures(),
        )
    }
}
