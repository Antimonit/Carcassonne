package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldScoringTest {

    @Test
    fun `field with unfinished city`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Coordinates(1, 0), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(1, -1), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_0), emptyList())

        assertEquals(0, board.fieldFeatures.mapNotNull { it.points(endGame = true) }.max())
    }

    @Test
    fun `finished field`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Coordinates(1, 0), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_90), emptyList())
            .placeTile(Coordinates(1, -1), Tiles.Basic.V.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(0, -1), Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180), emptyList())

        assertEquals(3, board.fieldFeatures.mapNotNull { it.points(endGame = true) }.max())
    }
}