package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.river.BB6F1
import me.khol.carcassonne.tiles.river.BB6F10
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.single
import kotlin.test.Test

class GardenFeaturesTest {

    @Test
    fun `garden feature`() {
        val board = Board.Companion.starting(startingTile = BB6F1)
            .placeTile(Coordinates(0, -1), RotatedTile(BB6F10, Rotation.ROTATE_180))

        expectThat(board)
            .get { getGardenFeatures() }
            .single()
            .isEqualTo(
                Feature.Garden(
                    garden = PlacedGardenGroup(Coordinates(0, -1), ElementGroup.Center),
                    neighborCount = 2,
                )
            )
            .get { isFinished }
            .isFalse()
    }
}
