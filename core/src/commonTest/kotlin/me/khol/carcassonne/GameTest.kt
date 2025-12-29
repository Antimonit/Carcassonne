package me.khol.carcassonne

import me.khol.carcassonne.fixtures.Players
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basicTileset
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GameTest {

    private val tileset = basicTileset
    private val startingTile = Tiles.Basic.D.tile
    private val game = Game.new(
        tilesets = listOf(tileset),
        startingTile = startingTile,
        players = listOf(Players.red, Players.green),
        random = Random(1),
    )

    @Test
    fun `remove only a single copy of the starting tile even if it contains multiple copies`() {
        assertTrue(tileset.tileCounts.first { it.tile == startingTile }.count >= 2)
        assertEquals(tileset.tileCounts.sumOf { it.count } - 1, game.remainingTiles.size)
    }

    @Test
    fun `remainingBoardSpaceCounts returns counts for all open spaces`() {
        val counts = game.remainingBoardSpaceCounts()

        assertEquals(4, counts.size)
        assertTrue(counts.values.all { it >= 0 })
    }

    @Test
    fun `remainingBoardSpaceCounts returns empty map when no remaining tiles`() {
        val game = game.copy(remainingTiles = emptyList())
        val counts = game.remainingBoardSpaceCounts()

        assertEquals(4, counts.size)
        assertTrue(counts.values.all { it == 0 })
    }

    @Test
    fun `remainingBoardSpaceCounts can not return more than the number of remaining tiles`() {
        val game = game.copy(remainingTiles = game.remainingTiles.take(5))
        val counts = game.remainingBoardSpaceCounts()

        assertEquals(4, counts.size)
        assertTrue(counts.values.all { it <= 5 })
    }
}
