package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class MonasteryScoringTest {

    @Test
    fun `unfinished monastery`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Coordinates(0, 1), Tiles.Basic.B.tile.rotated(Rotation.ROTATE_0), emptyList())

        assertEquals(2, board.monasteryFeatures.first().points(endGame = true))
    }

    @Test
    fun `finished monastery`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Coordinates(0, 1), Tiles.Basic.B.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 0), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 1), Tiles.Basic.U.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 2), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(0, 2), Tiles.Basic.U.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(-1, 2), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_180), emptyList())
            .placeTile(Coordinates(-1, 1), Tiles.Basic.U.tile.rotated(Rotation.ROTATE_180), emptyList())
            .placeTile(Coordinates(-1, 0), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_270), emptyList())

        assertEquals(9, board.monasteryFeatures.first().points(endGame = false))
    }
}