package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.placed
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldScoringTest {

    @Test
    fun `field with unfinished city`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Tiles.Basic.V.tile.rotated(Rotation.ROTATE_90).placed(1, 0), emptyList())
            .placeTile(Tiles.Basic.V.tile.rotated(Rotation.ROTATE_0).placed(1, -1), emptyList())

        assertEquals(0, board.fieldFeatures.mapNotNull { it.points(endGame = true) }.max())
    }

    @Test
    fun `finished field`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Tiles.Basic.V.tile.rotated(Rotation.ROTATE_90).placed(1, 0), emptyList())
            .placeTile(Tiles.Basic.V.tile.rotated(Rotation.ROTATE_0).placed(1, -1), emptyList())
            .placeTile(Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180).placed(0, -1), emptyList())

        assertEquals(3, board.fieldFeatures.mapNotNull { it.points(endGame = true) }.max())
    }
}