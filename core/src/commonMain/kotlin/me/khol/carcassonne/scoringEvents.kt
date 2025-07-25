package me.khol.carcassonne

import me.khol.carcassonne.feature.Feature
import me.khol.carcassonne.feature.getCityFeatures
import me.khol.carcassonne.feature.getFieldFeatures
import me.khol.carcassonne.feature.getGardenFeatures
import me.khol.carcassonne.feature.getMonasteryFeatures
import me.khol.carcassonne.feature.getRoadFeatures

fun scoringEvents(
    board: Board,
    currentPlayer: Player,
): List<History.Event.Scoring> {

    val fieldFeatures = board.getFieldFeatures()
    val roadFeatures = board.getRoadFeatures()
    val cityFeatures = board.getCityFeatures()
    val monasteryFeatures = board.getMonasteryFeatures()
    val gardenFeatures = board.getGardenFeatures()

    val figures: List<PlacedFigure> = board.figures.values.flatten()
    val scorableFeatures = figures.groupBy { figure: PlacedFigure ->
        when (figure.placedElement.element) {
            is Element.Field -> fieldFeatures.first { field -> figure.placedElement in field.placedFields }
            is Element.Road -> roadFeatures.first { road -> figure.placedElement in road.placedRoads }
            is Element.City -> cityFeatures.first { road -> figure.placedElement in road.placedCities }
            is Element.Monastery -> monasteryFeatures.first { monastery -> figure.placedElement == monastery.placedMonastery }
            is Element.Garden -> gardenFeatures.first { garden -> figure.placedElement == garden.placedGarden }
            else -> error("Unknown element type ${figure.placedElement.element::class}")
        }
    }

    // TODO: Ordering: Monastery, Garden, Road, City?

    return scorableFeatures.mapNotNull { (feature, placedFigures) ->
        when (val feature = feature) {
            is Feature.City if (feature.isFinished) -> {
                val modifier = if (feature.hasCathedral) 3 else 2
                val size = feature.placedCities.map { it.coordinates }.toSet().size
                val coatOfArms = feature.coatOfArms
                size * modifier + coatOfArms * 2
            }
            is Feature.Road if (feature.isFinished) -> {
                val modifier = if (feature.hasInn) 2 else 1
                val size = feature.placedRoads.map { it.coordinates }.toSet()
                size.size * modifier
            }
            is Feature.Garden if (feature.isFinished) -> {
                feature.neighborCount
            }
            is Feature.Monastery if (feature.isFinished) -> {
                feature.neighborCount
            }
            is Feature.Field -> null
            else -> null
        }?.let { points ->
            History.Event.Scoring(
                triggerPlayer = currentPlayer,
                scoringPlayers = placedFigures.maxPresence(),
                feature = feature,
                figures = placedFigures,
                points = points,
                board = board.removeFigures(placedFigures),
            )
        }
    }
}

private fun List<PlacedFigure>.maxPresence(): Set<Player> {
    val presence = groupingBy { it.figure.player }.eachCount()
    val maxPresence = presence.values.max()
    return presence.filterValues { it == maxPresence }.keys
}
