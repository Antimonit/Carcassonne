package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Boon
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class CityFeaturesTest {

    @Test
    fun `city feature`() {
        val board = Board.starting(startingTile = Tiles.Basic.D)

        board.getCityFeatures().run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.City(
                        cities = setOf(
                            PlacedCityGroup(Coordinates(0, 0), elementGroup = ElementGroup.City { top }),
                        ), isFinished = false
                    ),
                    actual = this,
                )
                assertEquals(0, coatOfArms)
            }
        }

        val newBoard = board
            .placeTile(Coordinates(0, -1), RotatedTile(Tiles.Basic.F, Rotation.ROTATE_90))
            .placeTile(Coordinates(0, -2), RotatedTile(Tiles.Basic.E, Rotation.ROTATE_180))

        newBoard.getCityFeatures().run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.City(
                        cities = setOf(
                            PlacedCityGroup(Coordinates(0, 0), elementGroup = ElementGroup.City { top }),
                            PlacedCityGroup(
                                Coordinates(0, -1),
                                elementGroup = ElementGroup.City(Boon.City.CoatOfArms) { top + bottom }),
                            PlacedCityGroup(Coordinates(0, -2), elementGroup = ElementGroup.City { bottom }),
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
            .starting(startingTile = Tiles.Basic.D)
            .placeTile(Coordinates(1, 0), RotatedTile(Tiles.Basic.K, Rotation.ROTATE_0))

        assertEquals(
            expected = setOf(
                Feature.City(
                    cities = setOf(
                        PlacedCityGroup(Coordinates(0, 0), elementGroup = ElementGroup.City { top }),
                    ),
                    isFinished = false,
                ),
                Feature.City(
                    cities = setOf(
                        PlacedCityGroup(Coordinates(1, 0), elementGroup = ElementGroup.City { top }),
                    ),
                    isFinished = false,
                ),
            ),
            actual = board.getCityFeatures(),
        )

        val newBoard = board
            .placeTile(Coordinates(0, -1), RotatedTile(Tiles.Basic.R, Rotation.ROTATE_90))
            .placeTile(Coordinates(1, -1), RotatedTile(Tiles.Basic.R, Rotation.ROTATE_270))

        assertEquals(
            expected = setOf(
                Feature.City(
                    cities = setOf(
                        PlacedCityGroup(Coordinates(0, 0), elementGroup = ElementGroup.City { top }),
                        PlacedCityGroup(Coordinates(1, 0), elementGroup = ElementGroup.City { top }),
                        PlacedCityGroup(Coordinates(0, -1), elementGroup = ElementGroup.City { top + right + bottom }),
                        PlacedCityGroup(Coordinates(1, -1), elementGroup = ElementGroup.City { top + left + bottom }),
                    ),
                    isFinished = false,
                ),
            ),
            actual = newBoard.getCityFeatures(),
        )
    }
}