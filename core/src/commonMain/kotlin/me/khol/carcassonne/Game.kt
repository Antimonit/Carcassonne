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

    fun remainingBoardSpaceCounts(): Map<Coordinates, Int> {
        return board.openSpaces.associateWith { centerSpace ->
            fun RotatedTile.rotatedEdges(): Tile.Edges = this.tile.edges.rotate(rotation)

            val top = board.getTile(centerSpace.top)?.rotatedEdges()?.bottom
            val right = board.getTile(centerSpace.right)?.rotatedEdges()?.left
            val bottom = board.getTile(centerSpace.bottom)?.rotatedEdges()?.top
            val left = board.getTile(centerSpace.left)?.rotatedEdges()?.right

            remainingTiles.count { tile ->
                Rotation.entries.map { rotation ->
                    tile.rotated(rotation)
                }.any { rotatedTile ->
                    listOfNotNull(
                        top?.let { rotatedTile.rotatedEdges().top == it },
                        bottom?.let { rotatedTile.rotatedEdges().bottom == it },
                        left?.let { rotatedTile.rotatedEdges().left == it },
                        right?.let { rotatedTile.rotatedEdges().right == it },
                    ).all { it }
                }
            }
        }
    }
}
