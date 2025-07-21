package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Boon
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
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
                        placedCities = setOf(
                            PlacedCity(Coordinates(0, 0), element = Element.City { top }),
                        ),
                        isFinished = false,
                    ),
                    actual = this,
                )
                assertEquals(0, coatOfArms)
            }
        }

        val newBoard = board
            .placeTile(Coordinates(0, -1), RotatedTile(Tiles.Basic.F, Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(0, -2), RotatedTile(Tiles.Basic.E, Rotation.ROTATE_180), emptyList())

        newBoard.getCityFeatures().run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.City(
                        placedCities = setOf(
                            PlacedCity(Coordinates(0, 0), element = Element.City { top }),
                            PlacedCity(Coordinates(0, -1), element = Element.City(Boon.City.CoatOfArms) { top + bottom }),
                            PlacedCity(Coordinates(0, -2), element = Element.City { bottom }),
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
            .placeTile(Coordinates(1, 0), RotatedTile(Tiles.Basic.K, Rotation.ROTATE_0), emptyList())

        assertEquals(
            expected = setOf(
                Feature.City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(0, 0), element = Element.City { top }),
                    ),
                    isFinished = false,
                ),
                Feature.City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(1, 0), element = Element.City { top }),
                    ),
                    isFinished = false,
                ),
            ),
            actual = board.getCityFeatures(),
        )

        val newBoard = board
            .placeTile(Coordinates(0, -1), RotatedTile(Tiles.Basic.R, Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(1, -1), RotatedTile(Tiles.Basic.R, Rotation.ROTATE_270), emptyList())

        assertEquals(
            expected = setOf(
                Feature.City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(0, 0), element = Element.City { top }),
                        PlacedCity(Coordinates(1, 0), element = Element.City { top }),
                        PlacedCity(Coordinates(0, -1), element = Element.City { top + right + bottom }),
                        PlacedCity(Coordinates(1, -1), element = Element.City { top + left + bottom }),
                    ),
                    isFinished = false,
                ),
            ),
            actual = newBoard.getCityFeatures(),
        )
    }
}