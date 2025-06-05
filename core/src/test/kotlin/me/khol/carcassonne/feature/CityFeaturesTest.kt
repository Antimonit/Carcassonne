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
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.isEqualTo
import strikt.assertions.single

class CityFeaturesTest {

    @Test
    fun `city feature`() {
        val board = Board.starting(startingTile = D)

        expectThat(board)
            .get { getCityFeatures() }
            .single()
            .isEqualTo(
                Feature.City(
                    cities = setOf(
                        PlacedCityGroup(Coordinates(0, 0), elementGroup = ElementGroup.city { top }),
                    ),
                    isFinished = false
                )
            )
            .get { coatOfArms }.isEqualTo(0)

        val newBoard = board
            .placeTile(Coordinates(0, 1), RotatedTile(F, Rotation.ROTATE_90))
            .placeTile(Coordinates(0, 2), RotatedTile(E, Rotation.ROTATE_180))

        expectThat(newBoard)
            .get { getCityFeatures() }
            .single()
            .isEqualTo(
                Feature.City(
                    cities = setOf(
                        PlacedCityGroup(Coordinates(0, 0), elementGroup = ElementGroup.city { top }),
                        PlacedCityGroup(Coordinates(0, 1), elementGroup = ElementGroup.city(Boon.City.CoatOfArms) { top + bottom }),
                        PlacedCityGroup(Coordinates(0, 2), elementGroup = ElementGroup.city { bottom }),
                    ),
                    isFinished = true,
                ),
            )
            .get { coatOfArms }.isEqualTo(1)
    }

    @Test
    fun `city features can merge`() {
        val board = Board
            .starting(startingTile = D)
            .placeTile(Coordinates(1, 0), RotatedTile(K, Rotation.ROTATE_0))

        expectThat(board)
            .get { getCityFeatures() }
            .containsExactlyInAnyOrder(
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
            )

        val newBoard = board
            .placeTile(Coordinates(0, 1), RotatedTile(R, Rotation.ROTATE_90))
            .placeTile(Coordinates(1, 1), RotatedTile(R, Rotation.ROTATE_270))

        expectThat(newBoard)
            .get { getCityFeatures() }
            .containsExactlyInAnyOrder(
                Feature.City(
                    cities = setOf(
                        PlacedCityGroup(Coordinates(0, 0), elementGroup = ElementGroup.city { top }),
                        PlacedCityGroup(Coordinates(1, 0), elementGroup = ElementGroup.city { top }),
                        PlacedCityGroup(Coordinates(0, 1), elementGroup = ElementGroup.city { top + right + bottom }),
                        PlacedCityGroup(Coordinates(1, 1), elementGroup = ElementGroup.city { top + left + bottom }),
                    ),
                    isFinished = false,
                ),
            )
    }
}