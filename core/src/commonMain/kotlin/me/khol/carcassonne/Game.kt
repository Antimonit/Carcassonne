package me.khol.carcassonne

import me.khol.carcassonne.tiles.Tileset
import kotlin.random.Random

data class Game(
    val tiles: List<Tile>,
    val remainingTiles: List<Tile>,
    val board: Board,
    val players: List<Player>,
    val currentPlayer: Player,
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

            val players = listOf(
                Player(name = "Green", color = Player.Color.Green),
                Player(name = "Red", color = Player.Color.Red),
            )

            return Game(
                tiles = tiles,
                remainingTiles = tiles.minus(startingTile).shuffled(random),
                board = Board.starting(startingTile = startingTile),
                players = players,
                currentPlayer = players.first(),
            )
        }
    }
}
