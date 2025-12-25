package me.khol.carcassonne

import me.khol.carcassonne.figure.Figure

data class FigureSupply(
    private val remainingFigureCount: Map<Player, Map<Figure, Int>>,
) {

    fun getCounts(player: Player): Map<Figure, Int> {
        return remainingFigureCount.getValue(player)
    }

    fun canPlace(player: Player, figure: Figure): Boolean {
        return remainingFigureCount.getValue(player).getValue(figure) > 0
    }
}

fun Board.figureSupply(players: List<Player>, maxFigureCounts: Map<Figure, Int>): FigureSupply {
    val placedFigures = figures.values
        .flatten().map { it.figure }
        .groupBy { it.player }
    val remainingFigureCount = players
        .associateWith {
            placedFigures.getOrElse(it) { emptyList() }
        }
        .mapValues { (_, figures) ->
            maxFigureCounts.mapValues { (figure, maxCount) ->
                maxCount - figures.count { it.figure == figure }
            }
        }

    return FigureSupply(remainingFigureCount = remainingFigureCount)
}