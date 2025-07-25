package me.khol.carcassonne

import me.khol.carcassonne.feature.Feature

data class History(
    val events: List<Event>,
) {

    sealed interface Event {

        data class TilePlacement(
            val player: Player,
            val placedTile: PlacedTile,
            val placedFigure: PlacedFigure?,
            val board: Board,
        ) : Event

        data class Scoring(
            val triggerPlayer: Player,
            val scoringPlayers: Set<Player>,
            val feature: Feature,
            val figures: List<PlacedFigure>,
            val points: Int,
            val board: Board,
        ) : Event
    }
}
