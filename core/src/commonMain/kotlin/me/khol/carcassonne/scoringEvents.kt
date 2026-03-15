package me.khol.carcassonne

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
        feature.points(endGame = false)?.let { points ->
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
