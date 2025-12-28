package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.rotated
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MonasteryFeaturesTest {

    @Test
    fun `monastery feature`() {
        val d = me.khol.carcassonne.tiles.basic.D
        val b = me.khol.carcassonne.tiles.basic.B
        val u = me.khol.carcassonne.tiles.basic.U
        val v = me.khol.carcassonne.tiles.basic.V

        val board = Board.starting(startingTile = d.tile)
            .placeTile(Coordinates(0, 1), b.tile.rotated(Rotation.ROTATE_0), emptyList())

        board.getMonasteryFeatures().run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.Monastery(
                        placedMonastery = PlacedMonastery(Coordinates(0, 1), Element.Monastery),
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
            .placeTile(Coordinates(1, 0), v.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 1), u.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 2), v.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(0, 2), u.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(-1, 2), v.tile.rotated(Rotation.ROTATE_180), emptyList())
            .placeTile(Coordinates(-1, 1), u.tile.rotated(Rotation.ROTATE_180), emptyList())
            .placeTile(Coordinates(-1, 0), v.tile.rotated(Rotation.ROTATE_270), emptyList())

        newBoard.getMonasteryFeatures().run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.Monastery(
                        placedMonastery = PlacedMonastery(Coordinates(0, 1), Element.Monastery),
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
