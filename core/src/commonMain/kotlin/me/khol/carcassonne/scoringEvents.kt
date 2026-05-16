package me.khol.carcassonne

import me.khol.carcassonne.feature.Feature

fun scoringEvent(
    board: Board,
    currentPlayer: Player,
): History.Event.Scoring? =
    board.allFeatures.firstNotNullOfOrNull { feature ->
        val figures = feature.figures.toSet()
        if (figures.isEmpty()) {
            null
        } else {
            feature.points(endGame = false)?.let { points ->
                History.Event.Scoring(
                    triggerPlayer = currentPlayer,
                    scoringPlayers = figures.map { it.figure }.maxPresence(feature),
                    feature = feature,
                    figures = figures,
                    points = points,
                    board = board.removeFigures(figures),
                )
            }
        }
    }

internal fun List<PlayerFigure>.maxPresence(feature: Feature): Set<Player> {
    val presence = groupingBy { it.player }
        .fold(0) { presence, it ->
            presence + it.figure.presence(feature)
        }
    val maxPresence = presence.values.maxOrNull()
    return presence.filterValues { it == maxPresence }.keys
}
