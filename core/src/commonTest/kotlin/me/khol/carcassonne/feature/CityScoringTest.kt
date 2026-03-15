package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class CityScoringTest {

    @Test
    fun `unfinished city`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Coordinates(0, -1), Tiles.Basic.F.tile.rotated(Rotation.ROTATE_90), emptyList())

        assertEquals(3, board.cityFeatures.first().points(endGame = true))
    }

    @Test
    fun `finished city`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Coordinates(0, -1), Tiles.Basic.F.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(0, -2), Tiles.Basic.E.tile.rotated(Rotation.ROTATE_180), emptyList())

        assertEquals(8, board.cityFeatures.first().points(endGame = false))
    }
}