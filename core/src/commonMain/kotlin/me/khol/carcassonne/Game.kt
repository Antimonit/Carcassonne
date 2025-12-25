package me.khol.carcassonne

import me.khol.carcassonne.figure.Abbot
import me.khol.carcassonne.figure.Builder
import me.khol.carcassonne.figure.Figure
import me.khol.carcassonne.figure.LargeMeeple
import me.khol.carcassonne.figure.Mayor
import me.khol.carcassonne.figure.Meeple
import me.khol.carcassonne.figure.Pig
import me.khol.carcassonne.tiles.Tileset
import kotlin.random.Random

data class Game(
    val tiles: List<Tile>,
    val remainingTiles: List<Tile>,
    val board: Board,
    val phase: Phase,
    val history: History,
    val players: List<Player>,
    val maxFigureCounts: Map<Figure, Int>,
    val scoreboard: Scoreboard,
    val currentPlayer: Player,
) {

    val figureSupply: FigureSupply = board.figureSupply(
        players = players,
        maxFigureCounts = maxFigureCounts,
    )

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
                maxFigureCounts = mapOf(
                    Meeple to 7,
                    Abbot to 1,
                    LargeMeeple to 1,
                    Mayor to 1,
                    Pig to 1,
                    Builder to 1,
                ),
                scoreboard = Scoreboard(players),
                currentPlayer = players.first(),
            )
        }
    }

    fun remainingBoardSpaceCounts(): Map<Coordinates, Int> {
        return board.openSpaces.associateWith { centerSpace ->
            remainingTiles.count { tile ->
                board.validTileRotations(centerSpace, tile).isNotEmpty()
            }
        }
    }
}
