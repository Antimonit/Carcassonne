package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.placed
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class CityFeaturesTest {

    @Test
    fun `city feature`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)

        board.cityFeatures.run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = City(
                        placedCities = setOf(
                            Tiles.Basic.D.city.rotated(Rotation.ROTATE_0).placed(0, 0),
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
            .placeTile(Tiles.Basic.F.tile.rotated(Rotation.ROTATE_90).placed(0, -1), emptyList())
            .placeTile(Tiles.Basic.E.tile.rotated(Rotation.ROTATE_180).placed(0, -2), emptyList())

        newBoard.cityFeatures.run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = City(
                        placedCities = setOf(
                            Tiles.Basic.D.city.rotated(Rotation.ROTATE_0).placed(0, 0),
                            Tiles.Basic.F.city.rotated(Rotation.ROTATE_90).placed(0, -1),
                            Tiles.Basic.E.city.rotated(Rotation.ROTATE_180).placed(0, -2),
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
            .placeTile(Tiles.Basic.K.tile.rotated(Rotation.ROTATE_0).placed(1, 0), emptyList())

        assertEquals(
            expected = setOf(
                City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(0, 0), rotatedElement = Tiles.Basic.D.city.rotated(Rotation.ROTATE_0)),
                    ),
                    isFinished = false,
                    figures = emptyList(),
                ),
                City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(1, 0), rotatedElement = Tiles.Basic.K.city.rotated(Rotation.ROTATE_0)),
                    ),
                    isFinished = false,
                    figures = emptyList(),
                ),
            ),
            actual = board.cityFeatures,
        )

        val newBoard = board
            .placeTile(Tiles.Basic.R.tile.rotated(Rotation.ROTATE_90).placed(0, -1), emptyList())
            .placeTile(Tiles.Basic.R.tile.rotated(Rotation.ROTATE_270).placed(1, -1), emptyList())

        assertEquals(
            expected = setOf(
                City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(0, 0), rotatedElement = Tiles.Basic.D.city.rotated(Rotation.ROTATE_0)),
                        PlacedCity(Coordinates(1, 0), rotatedElement = Tiles.Basic.K.city.rotated(Rotation.ROTATE_0)),
                        PlacedCity(Coordinates(0, -1), rotatedElement = Tiles.Basic.R.city.rotated(Rotation.ROTATE_90)),
                        PlacedCity(Coordinates(1, -1), rotatedElement = Tiles.Basic.R.city.rotated(Rotation.ROTATE_270)),
                    ),
                    isFinished = false,
                    figures = emptyList(),
                ),
            ),
            actual = newBoard.cityFeatures,
        )
    }

    @Test
    fun `city features with figures`() {
        val figureOne = PlacedFigure(
            placedElement = Tiles.Basic.K.city.rotated(Rotation.ROTATE_0).placed(1, 0),
            figure = PlayerFigures.greenMeeple,
        )
        val figureTwo = PlacedFigure(
            placedElement = Tiles.Basic.R.city.rotated(Rotation.ROTATE_90).placed(0, -1),
            figure = PlayerFigures.greenMeeple,
        )

        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Tiles.Basic.K.tile.rotated(Rotation.ROTATE_0).placed(1, 0), listOf(figureOne))
            .placeTile(Tiles.Basic.R.tile.rotated(Rotation.ROTATE_90).placed(0, -1), listOf(figureTwo))
            .placeTile(Tiles.Basic.R.tile.rotated(Rotation.ROTATE_270).placed(1, -1), emptyList())

        assertEquals(
            expected = setOf(
                setOf(figureOne, figureTwo)
            ),
            actual = board.cityFeatures.map { it.figures.toSet() }.toSet(),
        )
    }
}