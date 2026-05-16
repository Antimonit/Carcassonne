package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.placed
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MonasteryFeaturesTest {

    @Test
    fun `monastery feature`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Tiles.Basic.B.tile.rotated(Rotation.ROTATE_0).placed(0, 1), emptyList())

        board.monasteryFeatures.run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Monastery(
                        placedMonastery = Element.Monastery.rotated(Rotation.ROTATE_0).placed(0, 1),
                        neighborCount = 2,
                        figures = emptyList(),
                    ),
                    actual = this,
                )
                assertFalse(isFinished)
            }
        }

        // make a road circle around the monastery
        val newBoard = board
            .placeTile(Tiles.Basic.V.tile.rotated(Rotation.ROTATE_0).placed(1, 0), emptyList())
            .placeTile(Tiles.Basic.U.tile.rotated(Rotation.ROTATE_0).placed(1, 1), emptyList())
            .placeTile(Tiles.Basic.V.tile.rotated(Rotation.ROTATE_90).placed(1, 2), emptyList())
            .placeTile(Tiles.Basic.U.tile.rotated(Rotation.ROTATE_90).placed(0, 2), emptyList())
            .placeTile(Tiles.Basic.V.tile.rotated(Rotation.ROTATE_180).placed(-1, 2), emptyList())
            .placeTile(Tiles.Basic.U.tile.rotated(Rotation.ROTATE_180).placed(-1, 1), emptyList())
            .placeTile(Tiles.Basic.V.tile.rotated(Rotation.ROTATE_270).placed(-1, 0), emptyList())

        newBoard.monasteryFeatures.run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Monastery(
                        placedMonastery = Element.Monastery.rotated(Rotation.ROTATE_0).placed(0, 1),
                        neighborCount = 9,
                        figures = emptyList(),
                    ),
                    actual = this,
                )
                assertTrue(isFinished)
            }
        }
    }
}
