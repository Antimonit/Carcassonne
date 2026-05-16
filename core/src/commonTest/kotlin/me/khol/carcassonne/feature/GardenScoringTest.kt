package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.placed
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class GardenScoringTest {

    @Test
    fun `unfinished garden`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Tiles.Basic.E_G.tile.rotated(Rotation.ROTATE_180).placed(0, -1), emptyList())

        assertEquals(2, board.gardenFeatures.first().points(endGame = true))
    }

    @Test
    fun `finished garden`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Tiles.Basic.E_G.tile.rotated(Rotation.ROTATE_180).placed(0, -1), emptyList())
            .placeTile(Tiles.Basic.V.tile.rotated(Rotation.ROTATE_180).placed(-1, 0), emptyList())
            .placeTile(Tiles.Basic.U.tile.rotated(Rotation.ROTATE_180).placed(-1, -1), emptyList())
            .placeTile(Tiles.Basic.V.tile.rotated(Rotation.ROTATE_270).placed(-1, -2), emptyList())
            .placeTile(Tiles.Basic.U.tile.rotated(Rotation.ROTATE_90).placed(0, -2), emptyList())
            .placeTile(Tiles.Basic.V.tile.rotated(Rotation.ROTATE_0).placed(1, -2), emptyList())
            .placeTile(Tiles.Basic.U.tile.rotated(Rotation.ROTATE_0).placed(1, -1), emptyList())
            .placeTile(Tiles.Basic.V.tile.rotated(Rotation.ROTATE_90).placed(1, 0), emptyList())


        assertEquals(9, board.gardenFeatures.first().points(endGame = false))
    }
}