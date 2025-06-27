package me.khol.carcassonne

import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class BoardTest {

    @Test
    fun `possible spaces for a tile`() {
        val board = Board.starting(startingTile = Tiles.Basic.D)
        val newTile = Tiles.Basic.A
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
                    PlacedTile(RotatedTile(newTile, Rotation.ROTATE_0), Coordinates(x = +0, y = +1)),
                    PlacedTile(RotatedTile(newTile, Rotation.ROTATE_90), Coordinates(x = +0, y = +1)),
                    PlacedTile(RotatedTile(newTile, Rotation.ROTATE_270), Coordinates(x = +0, y = +1)),
                    PlacedTile(RotatedTile(newTile, Rotation.ROTATE_270), Coordinates(x = -1, y = +0)),
                    PlacedTile(RotatedTile(newTile, Rotation.ROTATE_90), Coordinates(x = +1, y = +0)),
                ),
                actual = values.flatten(),
            )
        }
    }

    @Test
    fun `placing a tile updates open spaces`() {
        val board = Board.starting(startingTile = Tiles.Basic.D)
        val newTile = Tiles.Basic.F
        val newBoard = board.placeTile(Coordinates(x = 0, y = 1), RotatedTile(newTile, Rotation.ROTATE_0))
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
        val board = Board.starting(startingTile = Tiles.Basic.D)
        assertFails {
            board.placeTile(Coordinates(x = 0, y = 0), RotatedTile(Tiles.Basic.D, Rotation.ROTATE_0))
        }
    }

    @Test
    fun `placing a tile out of bound of the current board should fail`() {
        val board = Board.starting(startingTile = Tiles.Basic.D)
        assertFails {
            board.placeTile(Coordinates(x = 2, y = 0), RotatedTile(Tiles.Basic.D, Rotation.ROTATE_0))
        }
    }

    @Test
    fun `placing a tile with non-matching edges should fail`() {
        val board = Board.starting(startingTile = Tiles.Basic.D)
        assertFails {
            board.placeTile(Coordinates(x = 1, y = 0), RotatedTile(Tiles.Basic.D, Rotation.ROTATE_90))
        }
    }
}
