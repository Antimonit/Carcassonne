package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.placed
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class CityScoringTest {

    @Test
    fun `unfinished city`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Tiles.Basic.F.tile.rotated(Rotation.ROTATE_90).placed(0, -1), emptyList())

        assertEquals(3, board.cityFeatures.first().points(endGame = true))
    }

    @Test
    fun `finished city`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Tiles.Basic.F.tile.rotated(Rotation.ROTATE_90).placed(0, -1), emptyList())
            .placeTile(Tiles.Basic.E.tile.rotated(Rotation.ROTATE_180).placed(0, -2), emptyList())

        assertEquals(8, board.cityFeatures.first().points(endGame = false))
    }
}