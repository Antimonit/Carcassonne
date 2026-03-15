package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class RoadScoringTest {

    @Test
    fun `unfinished road`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Coordinates(-1, 0), Tiles.Basic.A.tile.rotated(Rotation.ROTATE_270), emptyList())

        assertEquals(2, board.roadFeatures.first().points(endGame = true))
    }

    @Test
    fun `finished road`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(Coordinates(-1, 0), Tiles.Basic.A.tile.rotated(Rotation.ROTATE_270), emptyList())
            .placeTile(Coordinates(1, 0), Tiles.Basic.A.tile.rotated(Rotation.ROTATE_90), emptyList())

        assertEquals(3, board.roadFeatures.first().points(endGame = false))
    }
}