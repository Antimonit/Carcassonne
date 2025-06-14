package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.basic.B
import me.khol.carcassonne.tiles.basic.D
import me.khol.carcassonne.tiles.basic.U
import me.khol.carcassonne.tiles.basic.V
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue
import strikt.assertions.single
import kotlin.test.Test

class MonasteryFeaturesTest {

    @Test
    fun `monastery feature`() {
        val board = Board.Companion.starting(startingTile = D)
            .placeTile(Coordinates(0, -1), RotatedTile(B, Rotation.ROTATE_0))

        expectThat(board)
            .get { getMonasteryFeatures() }
            .single()
            .isEqualTo(
                Feature.Monastery(
                    monastery = PlacedMonasteryGroup(Coordinates(0, -1), ElementGroup.Center),
                    neighborCount = 2,
                )
            )
            .get { isFinished }
            .isFalse()

        // make a road circle around the monastery
        val newBoard = board
            .placeTile(Coordinates(1, 0), RotatedTile(V, Rotation.ROTATE_0))
            .placeTile(Coordinates(1, -1), RotatedTile(U, Rotation.ROTATE_0))
            .placeTile(Coordinates(1, -2), RotatedTile(V, Rotation.ROTATE_90))
            .placeTile(Coordinates(0, -2), RotatedTile(U, Rotation.ROTATE_90))
            .placeTile(Coordinates(-1, -2), RotatedTile(V, Rotation.ROTATE_180))
            .placeTile(Coordinates(-1, -1), RotatedTile(U, Rotation.ROTATE_180))
            .placeTile(Coordinates(-1, 0), RotatedTile(V, Rotation.ROTATE_270))

        expectThat(newBoard)
            .get { getMonasteryFeatures() }
            .single()
            .isEqualTo(
                Feature.Monastery(
                    monastery = PlacedMonasteryGroup(Coordinates(0, -1), ElementGroup.Center),
                    neighborCount = 9,
                )
            )
            .get { isFinished }
            .isTrue()
    }
}
