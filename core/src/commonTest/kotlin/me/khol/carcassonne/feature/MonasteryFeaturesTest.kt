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
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MonasteryFeaturesTest {

    @Test
    fun `monastery feature`() {
        val board = Board.starting(startingTile = D)
            .placeTile(Coordinates(0, 1), RotatedTile(B, Rotation.ROTATE_0))

        board.getMonasteryFeatures().run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.Monastery(
                        monastery = PlacedMonasteryGroup(Coordinates(0, 1), ElementGroup.Center),
                        neighborCount = 2,
                    ),
                    actual = this,
                )
                assertFalse(isFinished)
            }
        }

        // make a road circle around the monastery
        val newBoard = board
            .placeTile(Coordinates(1, 0), RotatedTile(V, Rotation.ROTATE_0))
            .placeTile(Coordinates(1, 1), RotatedTile(U, Rotation.ROTATE_0))
            .placeTile(Coordinates(1, 2), RotatedTile(V, Rotation.ROTATE_90))
            .placeTile(Coordinates(0, 2), RotatedTile(U, Rotation.ROTATE_90))
            .placeTile(Coordinates(-1, 2), RotatedTile(V, Rotation.ROTATE_180))
            .placeTile(Coordinates(-1, 1), RotatedTile(U, Rotation.ROTATE_180))
            .placeTile(Coordinates(-1, 0), RotatedTile(V, Rotation.ROTATE_270))

        newBoard.getMonasteryFeatures().run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.Monastery(
                        monastery = PlacedMonasteryGroup(Coordinates(0, 1), ElementGroup.Center),
                        neighborCount = 9,
                    ),
                    actual = this,
                )
                assertTrue(isFinished)
            }
        }
    }
}
