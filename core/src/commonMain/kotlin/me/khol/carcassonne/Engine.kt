package me.khol.carcassonne

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.figure.Abbot
import me.khol.carcassonne.figure.Meeple

class Engine(
    initialGame: Game,
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    private val scope = CoroutineScope(Job() + dispatcher)

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

    fun placeTile(phase: Phase.PlacingTile, placedTile: PlacedTile) {
        _game.update { game ->
            game.copy(phase = phase.copy(placedTile = placedTile))
        }
    }

    fun confirmTilePlacement(phase: Phase.PlacingTile) {
        val placedTile = phase.placedTile ?: return
        _game.update { game ->
            game.copy(
                phase = Phase.PlacingFigure(
                    placedTile = placedTile,
                    validTilePlacements = phase.validTilePlacements,
                    validFigurePlacements = game.board.validFigurePlacements(
                        placedTile = placedTile,
                        currentPlayer = game.currentPlayer,
                        figureSupply = game.figureSupply,
                    ),
                ),
            )
        }
    }

    fun placeFigure(phase: Phase.PlacingFigure, placedFigure: PlacedFigure) {
        _game.update { game ->
            game.copy(phase = phase.copy(placedFigure = placedFigure))
        }
    }

    fun confirmFigurePlacement(phase: Phase.PlacingFigure) {
        scope.launch {
            var nextGame = _game.value.let { game ->
                val placing = phase.placedTile
                val remainingTiles = game.remainingTiles.drop(1)
                val placedBoard = game.board.placeTile(
                    coordinates = placing.coordinates,
                    tile = placing.rotatedTile,
                    placedFigures = listOfNotNull(phase.placedFigure),
                )
                val tilePlacementEvent = History.Event.TilePlacement(
                    player = game.currentPlayer,
                    placedTile = phase.placedTile,
                    placedFigure = phase.placedFigure,
                    board = placedBoard.copy(),
                )
                game.copy(
                    board = placedBoard,
                    remainingTiles = remainingTiles,
                    history = game.history.addEvent(tilePlacementEvent),
                )
            }

            // We need to process one event at the time since in some expansions
            // players might take actions during scoring that trigger changes on
            // the board, such as removing meeples.
            do {
                val scoringEvent = scoringEvent(
                    board = nextGame.board,
                    currentPlayer = nextGame.currentPlayer,
                )

                if (scoringEvent != null) {
                    _game.value = nextGame.copy(
                        phase = Phase.Scoring(scoringEvent),
                    )
                    delay(2500)
                    nextGame = _game.value.let {
                        it.copy(
                            board = it.board.removeFigures(scoringEvent.figures),
                            history = it.history.addEvent(scoringEvent),
                            scoreboard = it.scoreboard.addScores(
                                players = scoringEvent.scoringPlayers,
                                points = scoringEvent.points,
                            ),
                        )
                    }
                }
            } while (scoringEvent != null)

            _game.value = nextGame.let {
                it.copy(
                    phase = it.remainingTiles.firstOrNull()
                        ?.let { nextTile ->
                            Phase.PlacingTile(
                                tile = nextTile,
                                validTilePlacements = it.board.possibleSpacesForTile(nextTile),
                            )
                        }
                        ?: Phase.FinalScoring,
                    currentPlayer = it.players.nextOf(it.currentPlayer),
                )
            }
        }
    }
}

fun Board.validFigurePlacements(
    placedTile: PlacedTile,
    currentPlayer: Player,
    figureSupply: FigureSupply,
): Map<RotatedElement<*>, List<PlacedFigure>> {
    val coordinates = placedTile.coordinates
    val rotatedTile = placedTile.rotatedTile

    val boardWithTile = copy(
        tiles = tiles + (coordinates to rotatedTile),
    )

    return rotatedTile.rotatedElements.all().associateWith { rotatedElement ->
        val placedElement = rotatedElement.placed(coordinates)
        val feature = boardWithTile.elementToFeature(placedElement)
        listOf(Meeple, Abbot/*, LargeMeeple, Mayor, Pig, Builder*/)
            .filter { figure -> figure.canBePlaced(feature, currentPlayer) }
            .filter { figure -> figureSupply.canPlace(currentPlayer, figure) }
            .map { PlayerFigure(figure = it, player = currentPlayer) }
            .map { PlacedFigure(placedElement = placedElement, figure = it) }
    }
}

fun <T> List<T>.nextOf(current: T): T {
    require(current in this) { "Could not find $current in $this" }
    return this[(indexOf(current) + 1) % size]
}
