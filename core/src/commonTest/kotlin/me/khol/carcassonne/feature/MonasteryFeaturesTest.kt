package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.Rotation
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
            .placeTile(Coordinates(0, 1), Tiles.Basic.B.tile.rotated(Rotation.ROTATE_0), emptyList())

        board.monasteryFeatures.run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.Monastery(
                        placedMonastery = PlacedMonastery(Coordinates(0, 1), Element.Monastery.rotated(Rotation.ROTATE_0)),
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
            .placeTile(Coordinates(1, 0), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 1), Tiles.Basic.U.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 2), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(0, 2), Tiles.Basic.U.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(-1, 2), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_180), emptyList())
            .placeTile(Coordinates(-1, 1), Tiles.Basic.U.tile.rotated(Rotation.ROTATE_180), emptyList())
            .placeTile(Coordinates(-1, 0), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_270), emptyList())

        newBoard.monasteryFeatures.run {
            assertEquals(1, size)

            first().run {
                assertEquals(
                    expected = Feature.Monastery(
                        placedMonastery = PlacedMonastery(Coordinates(0, 1), Element.Monastery.rotated(Rotation.ROTATE_0)),
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
