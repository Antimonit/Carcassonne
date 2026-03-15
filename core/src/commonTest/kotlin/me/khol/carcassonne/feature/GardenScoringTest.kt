package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class GardenScoringTest {

    @Test
    fun `unfinished garden`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Coordinates(0, -1), Tiles.Basic.E_G.tile.rotated(Rotation.ROTATE_180), emptyList())

        assertEquals(2, board.gardenFeatures.first().points(endGame = true))
    }

    @Test
    fun `finished garden`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Coordinates(0, -1), Tiles.Basic.E_G.tile.rotated(Rotation.ROTATE_180), emptyList())
            .placeTile(Coordinates(-1, 0), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_180), emptyList())
            .placeTile(Coordinates(-1, -1), Tiles.Basic.U.tile.rotated(Rotation.ROTATE_180), emptyList())
            .placeTile(Coordinates(-1, -2), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_270), emptyList())
            .placeTile(Coordinates(0, -2), Tiles.Basic.U.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(1, -2), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, -1), Tiles.Basic.U.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 0), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_90), emptyList())


        assertEquals(9, board.gardenFeatures.first().points(endGame = false))
    }
}