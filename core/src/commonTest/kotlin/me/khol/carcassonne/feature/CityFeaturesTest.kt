package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.rotated
import kotlin.test.Test
import kotlin.test.assertEquals

class CityFeaturesTest {

    @Test
    fun `city feature`() {
        val d = me.khol.carcassonne.tiles.basic.D
        val f = me.khol.carcassonne.tiles.basic.F
        val e = me.khol.carcassonne.tiles.basic.E
        val board = Board.starting(startingTile = d.tile)

        board.getCityFeatures().run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.City(
                        placedCities = setOf(
                            PlacedCity(Coordinates(0, 0), element = d.city.rotate(Rotation.ROTATE_0)),
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
            .placeTile(Coordinates(0, -1), f.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(0, -2), e.tile.rotated(Rotation.ROTATE_180), emptyList())

        newBoard.getCityFeatures().run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.City(
                        placedCities = setOf(
                            PlacedCity(Coordinates(0, 0), element = d.city.rotate(Rotation.ROTATE_0)),
                            PlacedCity(Coordinates(0, -1), element = f.city.rotate(Rotation.ROTATE_90)),
                            PlacedCity(Coordinates(0, -2), element = e.city.rotate(Rotation.ROTATE_180)),
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
        val d = me.khol.carcassonne.tiles.basic.D
        val k = me.khol.carcassonne.tiles.basic.K
        val r = me.khol.carcassonne.tiles.basic.R
        val board = Board
            .starting(startingTile = d.tile)
            .placeTile(Coordinates(1, 0), k.tile.rotated(Rotation.ROTATE_0), emptyList())

        assertEquals(
            expected = setOf(
                Feature.City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(0, 0), element = d.city.rotate(Rotation.ROTATE_0)),
                    ),
                    isFinished = false,
                    figures = emptyList(),
                ),
                Feature.City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(1, 0), element = k.city.rotate(Rotation.ROTATE_0)),
                    ),
                    isFinished = false,
                    figures = emptyList(),
                ),
            ),
            actual = board.getCityFeatures(),
        )

        val newBoard = board
            .placeTile(Coordinates(0, -1), r.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(1, -1), r.tile.rotated(Rotation.ROTATE_270), emptyList())

        assertEquals(
            expected = setOf(
                Feature.City(
                    placedCities = setOf(
                        PlacedCity(Coordinates(0, 0), element = d.city.rotate(Rotation.ROTATE_0)),
                        PlacedCity(Coordinates(1, 0), element = k.city.rotate(Rotation.ROTATE_0)),
                        PlacedCity(Coordinates(0, -1), element = r.city.rotate(Rotation.ROTATE_90)),
                        PlacedCity(Coordinates(1, -1), element = r.city.rotate(Rotation.ROTATE_270)),
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
        val d = me.khol.carcassonne.tiles.basic.D
        val k = me.khol.carcassonne.tiles.basic.K
        val r = me.khol.carcassonne.tiles.basic.R
        val figureOne = PlacedFigure(
            placedElement = k.city.placed(Coordinates(1, 0)),
            figure = PlayerFigures.greenMeeple,
        )
        val figureTwo = PlacedFigure(
            placedElement = r.city.rotate(Rotation.ROTATE_90).placed(Coordinates(0, -1)),
            figure = PlayerFigures.greenMeeple,
        )

        val board = Board.starting(startingTile = d.tile)
            .placeTile(Coordinates(1, 0), k.tile.rotated(Rotation.ROTATE_0), listOf(figureOne))
            .placeTile(Coordinates(0, -1), r.tile.rotated(Rotation.ROTATE_90), listOf(figureTwo))
            .placeTile(Coordinates(1, -1), r.tile.rotated(Rotation.ROTATE_270), emptyList())

        assertEquals(
            expected = setOf(
                setOf(figureOne, figureTwo)
            ),
            actual = board.getCityFeatures().map { it.figures.toSet() }.toSet(),
        )
    }
}