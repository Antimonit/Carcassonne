package me.khol.carcassonne

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
            }

            return Game(
                tiles = tiles,
                remainingTiles = tiles.minus(startingTile).shuffled(random),
                board = Board.starting(startingTile = startingTile),
            )
        }
    }
}
