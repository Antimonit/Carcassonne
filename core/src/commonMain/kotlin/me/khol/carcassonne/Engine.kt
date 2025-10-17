package me.khol.carcassonne

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.khol.carcassonne.feature.PlacedElement
import kotlin.collections.plus

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
            game.copy(
                phase = Phase.PlacingFigure.Placed(
                    tile = tile,
                    placedFigure = PlacedFigure(
                        placedElement = PlacedElement(tile.coordinates, element),
                        figure = PlayerFigure(
                            figure = Figure.Meeple,
                            player = game.currentPlayer,
                        ),
                    ),
                    validElements = game.board.validElements(tile),
                )
            )
        }
    }

    fun confirmFigurePlacement(phase: Phase.PlacingFigure) {
        val placing = phase.tile
        _game.update { game ->
            val remainingTiles = game.remainingTiles.drop(1)
            val board = game.board.placeTile(
                coordinates = placing.coordinates,
                tile = placing.rotatedTile,
                placedFigures = when (phase) {
                    is Phase.PlacingFigure.Fresh -> emptyList()
                    is Phase.PlacingFigure.Placed -> listOf(
                        PlacedFigure(
                            placedElement = PlacedElement(
                                coordinates = placing.coordinates,
                                element = phase.placedFigure.placedElement.element,
                            ),
                            figure = PlayerFigure(
                                figure = Figure.Meeple,
                                player = game.currentPlayer,
                            ),
                        ),
                    )
                },
            )
            game.copy(
                board = board,
                remainingTiles = remainingTiles,
                history = game.history.copy(
                    events = game.history.events + History.Event.TilePlacement(
                        player = game.currentPlayer,
                        placedTile = phase.tile,
                        placedFigure = when (phase) {
                            is Phase.PlacingFigure.Fresh -> null
                            is Phase.PlacingFigure.Placed -> phase.placedFigure
                        },
                        board = board.copy(),
                    )
                ),
                phase = remainingTiles.firstOrNull()
                    ?.let(Phase.PlacingTile::Fresh)
                    ?: Phase.FinalScoring,
                currentPlayer = game.players.nextOf(game.currentPlayer),
            )
        }
    }

    fun confirmTilePlacement(phase: Phase.PlacingTile.Placed) {
        val placing = phase.placedTile
        _game.update { game ->
            game.copy(
                phase = Phase.PlacingFigure.Fresh(
                    tile = placing,
                    validElements = game.board.validElements(placing),
                ),
            )
        }
    }
}

fun Board.validElements(placedTile: PlacedTile): Set<Element<*>> {
    val coordinates = placedTile.coordinates
    val rotatedTile = placedTile.rotatedTile

    val boardWithTile = copy(
        tiles = tiles + (coordinates to rotatedTile),
    )

    val validElements = rotatedTile.tile.elements.all()
        .filter {
            val element = PlacedElement(
                coordinates = coordinates,
                element = it.rotate(rotatedTile.rotation),
            )
            val feature = boardWithTile.elementToFeature(element)
            val featureFigures = boardWithTile.findFiguresForFeature(feature)
            featureFigures.isEmpty()
        }
        .toSet()

    return validElements
}

fun <T> List<T>.nextOf(current: T): T = this[(indexOf(current) + 1) % size]
