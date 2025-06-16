package me.khol.carcassonne.ui

import me.khol.carcassonne.Board
import me.khol.carcassonne.Tile
import me.khol.carcassonne.tiles.Tileset
import kotlin.random.Random

data class Game(
    val tiles: List<Tile>,
    val remainingTiles: List<Tile>,
    val board: Board,
) {

    val currentTile: Tile? =
        remainingTiles.firstOrNull()

    companion object {

        fun new(
            tilesets: List<Tileset>,
            startingTile: Tile,
            random: Random = Random(42),
        ): Game {
            val tiles = tilesets.flatMap { tileSet ->
                tileSet.tileCounts.flatMap { tileCount ->
                    List(tileCount.count) {
                        tileCount.tile
                    }
                }
            }.shuffled(random)

            return Game(
                tiles = tiles,
                remainingTiles = tiles - startingTile,
                board = Board.starting(startingTile = startingTile),
            )
        }
    }
}
