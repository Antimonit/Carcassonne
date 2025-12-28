package me.khol.carcassonne

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.figure.Abbot
import me.khol.carcassonne.figure.Meeple

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

    fun placeFigure(tile: PlacedTile, placedFigure: PlacedFigure) {
        _game.update { game ->
            game.copy(
                phase = Phase.PlacingFigure.Placed(
                    placedTile = tile,
                    placedFigure = placedFigure,
                    validFigurePlacements = game.board.validFigurePlacements(tile, game.currentPlayer),
                )
            )
        }
    }

    fun confirmFigurePlacement(phase: Phase.PlacingFigure) {
        val placing = phase.placedTile
        _game.update { game ->
            val remainingTiles = game.remainingTiles.drop(1)
            val placedBoard = game.board.placeTile(
                coordinates = placing.coordinates,
                tile = placing.rotatedTile,
                placedFigures = when (phase) {
                    is Phase.PlacingFigure.Fresh -> emptyList()
                    is Phase.PlacingFigure.Placed -> listOf(phase.placedFigure)
                },
            )

            val scoringEvents = scoringEvents(
                board = placedBoard,
                currentPlayer = game.currentPlayer,
            )

            val tilePlacementEvent = History.Event.TilePlacement(
                player = game.currentPlayer,
                placedTile = phase.placedTile,
                placedFigure = when (phase) {
                    is Phase.PlacingFigure.Fresh -> null
                    is Phase.PlacingFigure.Placed -> phase.placedFigure
                },
                board = placedBoard.copy(),
            )

            val scoredBoard = placedBoard.removeFigures(scoringEvents.flatMap { it.figures })

            game.copy(
                board = scoredBoard,
                remainingTiles = remainingTiles,
                history = game.history.copy(
                    events = game.history.events + tilePlacementEvent + scoringEvents
                ),
                phase = remainingTiles.firstOrNull()
                    ?.let(Phase.PlacingTile::Fresh)
                    ?: Phase.FinalScoring,
                scoreboard = scoringEvents.fold(game.scoreboard) { scoreboard, score ->
                    score.scoringPlayers.fold(scoreboard) { scoreboard, player ->
                        scoreboard.addScore(player = player, points = score.points)
                    }
                },
                currentPlayer = game.players.nextOf(game.currentPlayer),
            )
        }
    }

    fun confirmTilePlacement(phase: Phase.PlacingTile.Placed) {
        val placing = phase.placedTile
        _game.update { game ->
            game.copy(
                phase = Phase.PlacingFigure.Fresh(
                    placedTile = placing,
                    validFigurePlacements = game.board.validFigurePlacements(placing, game.currentPlayer),
                ),
            )
        }
    }
}


fun Board.validFigurePlacements(placedTile: PlacedTile, currentPlayer: Player): Map<Element<*>, List<PlacedFigure>> {
    val coordinates = placedTile.coordinates
    val rotatedTile = placedTile.rotatedTile

    val boardWithTile = copy(
        tiles = tiles + (coordinates to rotatedTile),
    )

    return rotatedTile.tile.elements.all()
        .associate { element ->
            val element = element.rotate(rotatedTile.rotation).placed(coordinates)
            val feature = boardWithTile.elementToFeature(element)
            val validFigurePlacements =
                listOf(Meeple, Abbot/*, LargeMeeple, Mayor, Pig, Builder*/)
                    .filter { figure -> figure.canBePlaced(feature, currentPlayer) }
                    .map { PlayerFigure(figure = it, player = currentPlayer) }
                    .map { PlacedFigure(placedElement = element, figure = it) }

            element.element to validFigurePlacements
        }
}

fun <T> List<T>.nextOf(current: T): T {
    require(current in this) { "Could not find $current in $this" }
    return this[(indexOf(current) + 1) % size]
}
