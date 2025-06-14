package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Boon
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.basic.D
import me.khol.carcassonne.tiles.basic.E
import me.khol.carcassonne.tiles.basic.F
import me.khol.carcassonne.tiles.basic.K
import me.khol.carcassonne.tiles.basic.R
import kotlin.test.Test
import kotlin.test.assertEquals

class CityFeaturesTest {

    @Test
    fun `city feature`() {
        val board = Board.starting(startingTile = D)

        board.getCityFeatures().run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.City(
                        cities = setOf(
                            PlacedCityGroup(Coordinates(0, 0), elementGroup = ElementGroup.city { top }),
                        ), isFinished = false
                    ),
                    actual = this,
                )
                assertEquals(0, coatOfArms)
            }
        }

        val newBoard = board
            .placeTile(Coordinates(0, 1), RotatedTile(F, Rotation.ROTATE_90))
            .placeTile(Coordinates(0, 2), RotatedTile(E, Rotation.ROTATE_180))

        newBoard.getCityFeatures().run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.City(
                        cities = setOf(
                            PlacedCityGroup(Coordinates(0, 0), elementGroup = ElementGroup.city { top }),
                            PlacedCityGroup(
                                Coordinates(0, 1),
                                elementGroup = ElementGroup.city(Boon.City.CoatOfArms) { top + bottom }),
                            PlacedCityGroup(Coordinates(0, 2), elementGroup = ElementGroup.city { bottom }),
                        ),
                        isFinished = true,
                    ),
                    actual = this,
                )
                assertEquals(1, coatOfArms)
            }
        }
    }

    @Test
    fun `city features can merge`() {
        val board = Board
            .starting(startingTile = D)
            .placeTile(Coordinates(1, 0), RotatedTile(K, Rotation.ROTATE_0))

        assertEquals(
            expected = setOf(
                Feature.City(
                    cities = setOf(
                        PlacedCityGroup(Coordinates(0, 0), elementGroup = ElementGroup.city { top }),
                    ),
                    isFinished = false,
                ),
                Feature.City(
                    cities = setOf(
                        PlacedCityGroup(Coordinates(1, 0), elementGroup = ElementGroup.city { top }),
                    ),
                    isFinished = false,
                ),
            ),
            actual = board.getCityFeatures(),
        )

        val newBoard = board
            .placeTile(Coordinates(0, 1), RotatedTile(R, Rotation.ROTATE_90))
            .placeTile(Coordinates(1, 1), RotatedTile(R, Rotation.ROTATE_270))

        assertEquals(
            expected = setOf(
                Feature.City(
                    cities = setOf(
                        PlacedCityGroup(Coordinates(0, 0), elementGroup = ElementGroup.city { top }),
                        PlacedCityGroup(Coordinates(1, 0), elementGroup = ElementGroup.city { top }),
                        PlacedCityGroup(Coordinates(0, 1), elementGroup = ElementGroup.city { top + right + bottom }),
                        PlacedCityGroup(Coordinates(1, 1), elementGroup = ElementGroup.city { top + left + bottom }),
                    ),
                    isFinished = false,
                ),
            ),
            actual = newBoard.getCityFeatures(),
        )
    }
}