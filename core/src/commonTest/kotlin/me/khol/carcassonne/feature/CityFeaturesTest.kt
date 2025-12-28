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

class CityFeaturesTest {

    @Test
    fun `city feature`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)

        board.getCityFeatures().run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.City(
                        placedCities = setOf(
                            PlacedCity(Coordinates(0, 0), element = Tiles.Basic.D.city.rotate(Rotation.ROTATE_0)),
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
            .placeTile(Coordinates(0, -1), Tiles.Basic.F.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(0, -2), Tiles.Basic.E.tile.rotated(Rotation.ROTATE_180), emptyList())

        newBoard.getCityFeatures().run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.City(
                        placedCities = setOf(
                            PlacedCity(Coordinates(0, 0), element = Tiles.Basic.D.city.rotate(Rotation.ROTATE_0)),
                            PlacedCity(Coordinates(0, -1), element = Tiles.Basic.F.city.rotate(Rotation.ROTATE_90)),
                            PlacedCity(Coordinates(0, -2), element = Tiles.Basic.E.city.rotate(Rotation.ROTATE_180)),
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
            .starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Coordinates(1, 0), Tiles.Basic.K.tile.rotated(Rotation.ROTATE_0), emptyList())

        assertEquals(
            expected = setOf(
                Feature.City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(0, 0), element = Tiles.Basic.D.city.rotate(Rotation.ROTATE_0)),
                    ),
                    isFinished = false,
                    figures = emptyList(),
                ),
                Feature.City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(1, 0), element = Tiles.Basic.K.city.rotate(Rotation.ROTATE_0)),
                    ),
                    isFinished = false,
                    figures = emptyList(),
                ),
            ),
            actual = board.getCityFeatures(),
        )

        val newBoard = board
            .placeTile(Coordinates(0, -1), Tiles.Basic.R.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(1, -1), Tiles.Basic.R.tile.rotated(Rotation.ROTATE_270), emptyList())

        assertEquals(
            expected = setOf(
                Feature.City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(0, 0), element = Tiles.Basic.D.city.rotate(Rotation.ROTATE_0)),
                        PlacedCity(Coordinates(1, 0), element = Tiles.Basic.K.city.rotate(Rotation.ROTATE_0)),
                        PlacedCity(Coordinates(0, -1), element = Tiles.Basic.R.city.rotate(Rotation.ROTATE_90)),
                        PlacedCity(Coordinates(1, -1), element = Tiles.Basic.R.city.rotate(Rotation.ROTATE_270)),
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
            placedElement = Tiles.Basic.K.city.placed(Coordinates(1, 0)),
            figure = PlayerFigures.greenMeeple,
        )
        val figureTwo = PlacedFigure(
            placedElement = Tiles.Basic.R.city.rotate(Rotation.ROTATE_90).placed(Coordinates(0, -1)),
            figure = PlayerFigures.greenMeeple,
        )

        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Coordinates(1, 0), Tiles.Basic.K.tile.rotated(Rotation.ROTATE_0), listOf(figureOne))
            .placeTile(Coordinates(0, -1), Tiles.Basic.R.tile.rotated(Rotation.ROTATE_90), listOf(figureTwo))
            .placeTile(Coordinates(1, -1), Tiles.Basic.R.tile.rotated(Rotation.ROTATE_270), emptyList())

        assertEquals(
            expected = setOf(
                setOf(figureOne, figureTwo)
            ),
            actual = board.getCityFeatures().map { it.figures.toSet() }.toSet(),
        )
    }
}