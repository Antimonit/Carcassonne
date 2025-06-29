package me.khol.carcassonne

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Engine(
    initialGame: Game,
) {

    private val _game = MutableStateFlow(initialGame)
    val game: StateFlow<Game> = _game.asStateFlow()

    fun undo() {
        _game.update { game ->
            if (game.phase is Phase.Undoable) {
                game.copy(phase = game.phase.undo())
            } else {
                game
            }
        }
    }

    fun placeTile(tile: PlacedTile) {
        _game.update { game ->
            game.copy(phase = Phase.PlacingTile.Placed(placedTile = tile))
        }
    }

    fun placeFigure(tile: PlacedTile, element: Element<*>) {
        _game.update { game ->
            game.copy(phase = Phase.PlacingFigure.Placed(tile, element))
        }
    }

    fun confirmFigurePlacement(phase: Phase.PlacingFigure) {
        val placing = phase.tile
        _game.update { game ->
            val remainingTiles = game.remainingTiles.drop(1)
            game.copy(
                board = game.board.placeTile(
                    coordinates = placing.coordinates,
                    tile = placing.rotatedTile,
                ),
                remainingTiles = remainingTiles,
                phase = remainingTiles.firstOrNull()
                    ?.let(Phase.PlacingTile::Fresh)
                    ?: Phase.FinalScoring
            )
        }
    }

    fun confirmTilePlacement(phase: Phase.PlacingTile.Placed) {
        val placing = phase.placedTile
        _game.update { game ->
            game.copy(phase = Phase.PlacingFigure.Fresh(placing))
        }
    }
}
