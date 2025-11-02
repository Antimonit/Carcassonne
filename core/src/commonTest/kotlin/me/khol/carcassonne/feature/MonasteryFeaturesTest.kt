package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MonasteryFeaturesTest {

    @Test
    fun `monastery feature`() {
        val board = Board.starting(startingTile = Tiles.Basic.D)
            .placeTile(Coordinates(0, 1), RotatedTile(Tiles.Basic.B, Rotation.ROTATE_0), emptyList())

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
            .placeTile(Coordinates(1, 0), RotatedTile(Tiles.Basic.V, Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 1), RotatedTile(Tiles.Basic.U, Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 2), RotatedTile(Tiles.Basic.V, Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(0, 2), RotatedTile(Tiles.Basic.U, Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(-1, 2), RotatedTile(Tiles.Basic.V, Rotation.ROTATE_180), emptyList())
            .placeTile(Coordinates(-1, 1), RotatedTile(Tiles.Basic.U, Rotation.ROTATE_180), emptyList())
            .placeTile(Coordinates(-1, 0), RotatedTile(Tiles.Basic.V, Rotation.ROTATE_270), emptyList())

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
