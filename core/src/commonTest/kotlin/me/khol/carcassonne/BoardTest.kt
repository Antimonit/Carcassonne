package me.khol.carcassonne

import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class BoardTest {

    @Test
    fun `possible spaces for a tile`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
        val newTile = Tiles.Basic.A.tile
        board.possibleSpacesForTile(newTile).run {
            assertEquals(
                expected = setOf(
                    Coordinates(x = +0, y = +1),
                    Coordinates(x = -1, y = +0),
                    Coordinates(x = +1, y = +0),
                ),
                actual = keys,
            )
            assertEquals(
                expected = listOf(
                    newTile.rotated(Rotation.ROTATE_0).placed(x = +0, y = +1),
                    newTile.rotated(Rotation.ROTATE_90).placed(x = +0, y = +1),
                    newTile.rotated(Rotation.ROTATE_270).placed(x = +0, y = +1),
                    newTile.rotated(Rotation.ROTATE_270).placed(x = -1, y = +0),
                    newTile.rotated(Rotation.ROTATE_90).placed(x = +1, y = +0),
                ),
                actual = values.flatten(),
            )
        }
    }

    @Test
    fun `placing a tile updates open spaces`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
        val newTile = Tiles.Basic.F.tile
        val newBoard = board.placeTile(newTile.rotated(Rotation.ROTATE_0).placed(0, 1), emptyList())
        assertEquals(
            expected = setOf(
                Coordinates(x = 0, y = -1),
                Coordinates(x = -1, y = 1),
                Coordinates(x = 1, y = 1),
                Coordinates(x = 0, y = 2),
            ),
            actual = newBoard.possibleSpacesForTile(newTile).keys,
        )
    }

    @Test
    fun `placing a tile where another tile is already located should fail`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
        assertFails {
            board.placeTile(Tiles.Basic.D.tile.rotated(Rotation.ROTATE_0).placed(0, 0), emptyList())
        }
    }

    @Test
    fun `placing a tile out of bound of the current board should fail`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
        assertFails {
            board.placeTile(Tiles.Basic.D.tile.rotated(Rotation.ROTATE_0).placed(2, 0), emptyList())
        }
    }

    @Test
    fun `placing a tile with non-matching edges should fail`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
        assertFails {
            board.placeTile(Tiles.Basic.D.tile.rotated(Rotation.ROTATE_90).placed(1, 0), emptyList())
        }
    }
}
