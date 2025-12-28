package me.khol.carcassonne

import me.khol.carcassonne.tiles.Tileset
import kotlin.random.Random

data class Game(
    val tiles: List<Tile>,
    val remainingTiles: List<Tile>,
    val board: Board,
    val phase: Phase,
    val history: History,
    val players: List<Player>,
    val scoreboard: Scoreboard,
    val currentPlayer: Player,
) {

    companion object {

        fun new(
            tilesets: List<Tileset>,
            startingTile: Tile,
            players: List<Player>,
            random: Random = Random,
        ): Game {
            val tiles = tilesets.flatMap { tileSet ->
                tileSet.tileCounts.flatMap { tileCount ->
                    List(tileCount.count) {
                        tileCount.tile
                    }
                }
            }

            val remainingTiles = tiles.minus(startingTile).shuffled(random)

            return Game(
                tiles = tiles,
                remainingTiles = remainingTiles,
                board = Board.starting(startingTile = startingTile),
                phase = remainingTiles.firstOrNull()
                    ?.let { Phase.PlacingTile.Fresh(tile = it) }
                    ?: Phase.FinalScoring,
                history = History(events = emptyList()),
                players = players,
                scoreboard = Scoreboard(players),
                currentPlayer = players.first(),
            )
        }
    }

    fun remainingBoardSpaceCounts(): Map<Coordinates, Int> =
        board.openSpaces.associateWith { centerSpace ->
            val top = board.getTile(centerSpace.top)?.edges?.bottom
            val right = board.getTile(centerSpace.right)?.edges?.left
            val bottom = board.getTile(centerSpace.bottom)?.edges?.top
            val left = board.getTile(centerSpace.left)?.edges?.right

            remainingTiles.count { tile ->
                Rotation.entries.map { rotation ->
                    tile.rotated(rotation)
                }.any { rotatedTile ->
                    listOfNotNull(
                        top?.let { rotatedTile.edges.top == it },
                        bottom?.let { rotatedTile.edges.bottom == it },
                        left?.let { rotatedTile.edges.left == it },
                        right?.let { rotatedTile.edges.right == it },
                    ).all { it }
                }
            }
        }
}
