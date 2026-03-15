package me.khol.carcassonne

import me.khol.carcassonne.feature.City
import me.khol.carcassonne.feature.Field
import me.khol.carcassonne.feature.Garden
import me.khol.carcassonne.feature.Monastery
import me.khol.carcassonne.feature.Road

fun scoringEvent(
    board: Board,
    currentPlayer: Player,
): History.Event.Scoring? {

    val fieldFeatures = board.fieldFeatures
    val roadFeatures = board.roadFeatures
    val cityFeatures = board.cityFeatures
    val monasteryFeatures = board.monasteryFeatures
    val gardenFeatures = board.gardenFeatures

    val figures: List<PlacedFigure> = board.figures.values.flatten()
    val scorableFeatures = figures.groupBy { figure: PlacedFigure ->
        when (figure.placedElement.rotatedElement.element) {
            is Element.Field -> fieldFeatures.first { field -> figure.placedElement in field.placedFields }
            is Element.Road -> roadFeatures.first { road -> figure.placedElement in road.placedRoads }
            is Element.City -> cityFeatures.first { road -> figure.placedElement in road.placedCities }
            is Element.Monastery -> monasteryFeatures.first { monastery -> figure.placedElement == monastery.placedMonastery }
            is Element.Garden -> gardenFeatures.first { garden -> figure.placedElement == garden.placedGarden }
            else -> error("Unknown element type ${figure.placedElement.rotatedElement.element::class}")
        }
    }

    // TODO: Ordering: Monastery, Garden, Road, City?

    return scorableFeatures.firstNotNullOfOrNull { (feature, placedFigures) ->
        when (val feature = feature) {
            is City if (feature.isFinished) -> {
                val modifier = if (feature.hasCathedral) 3 else 2
                val size = feature.placedCities.map { it.coordinates }.toSet().size
                val coatOfArms = feature.coatOfArms
                size * modifier + coatOfArms * 2
            }
            is Road if (feature.isFinished) -> {
                val modifier = if (feature.hasInn) 2 else 1
                val size = feature.placedRoads.map { it.coordinates }.toSet()
                size.size * modifier
            }
            is Garden if (feature.isFinished) -> {
                feature.neighborCount
            }
            is Monastery if (feature.isFinished) -> {
                feature.neighborCount
            }
            is Field -> null
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
