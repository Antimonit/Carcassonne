package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Boon
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basic.K
import me.khol.carcassonne.tiles.basic.R
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
                        figures = emptyList(),
                    ),
                    actual = this,
                )
                assertEquals(0, coatOfArms)
            }
        }

        val newBoard = board
            .placeTile(Coordinates(0, -1), Tiles.Basic.F.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(0, -2), Tiles.Basic.E.rotated(Rotation.ROTATE_180), emptyList())

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
                        figures = emptyList(),
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
            .placeTile(Coordinates(1, 0), Tiles.Basic.K.rotated(Rotation.ROTATE_0), emptyList())

        assertEquals(
            expected = setOf(
                Feature.City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(0, 0), element = Element.City { top }),
                    ),
                    isFinished = false,
                    figures = emptyList(),
                ),
                Feature.City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(1, 0), element = Element.City { top }),
                    ),
                    isFinished = false,
                    figures = emptyList(),
                ),
            ),
            actual = board.getCityFeatures(),
        )

        val newBoard = board
            .placeTile(Coordinates(0, -1), Tiles.Basic.R.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(1, -1), Tiles.Basic.R.rotated(Rotation.ROTATE_270), emptyList())

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
                    figures = emptyList(),
                ),
            ),
            actual = newBoard.getCityFeatures(),
        )
    }

    @Test
    fun `city features with figures`() {
        val figureOne = PlacedFigure(
            placedElement = PlacedElement(
                coordinates = Coordinates(1, 0),
                element = K.city,
            ),
            figure = PlayerFigures.greenMeeple,
        )
        val figureTwo = PlacedFigure(
            placedElement = PlacedElement(
                coordinates = Coordinates(0, -1),
                element = R.city.rotate(Rotation.ROTATE_90),
            ),
            figure = PlayerFigures.greenMeeple,
        )

        val board = Board.starting(startingTile = Tiles.Basic.D)
            .placeTile(Coordinates(1, 0), Tiles.Basic.K.rotated(Rotation.ROTATE_0), listOf(figureOne))
            .placeTile(Coordinates(0, -1), Tiles.Basic.R.rotated(Rotation.ROTATE_90), listOf(figureTwo))
            .placeTile(Coordinates(1, -1), Tiles.Basic.R.rotated(Rotation.ROTATE_270), emptyList())

        assertEquals(
            expected = setOf(
                setOf(figureOne, figureTwo)
            ),
            actual = board.getCityFeatures().map { it.figures.toSet() }.toSet(),
        )
    }
}