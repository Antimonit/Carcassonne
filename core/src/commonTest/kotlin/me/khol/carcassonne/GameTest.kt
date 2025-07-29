package me.khol.carcassonne

import me.khol.carcassonne.fixtures.Players
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basicTileset
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GameTest {

    @Test
    fun `remove only a single copy of the starting tile even if it contains multiple copies`() {
        val tileset = basicTileset
        val startingTile = Tiles.Basic.D
        val game = Game.new(
            tilesets = listOf(tileset),
            startingTile = startingTile,
            players = listOf(Players.red, Players.green),
            random = Random(1),
        )

        assertTrue(tileset.tileCounts.first { it.tile == startingTile }.count >= 2)
        assertEquals(tileset.tileCounts.sumOf { it.count } - 1, game.remainingTiles.size)
    }
}
