package me.khol.carcassonne

data class History(
    val events: List<Event>,
) {

    sealed interface Event {

        class TilePlacement(
            val player: Player,
            val placedTile: PlacedTile,
            val placedFigure: PlacedFigure?,
            val board: Board,
        ) : Event
    }
}
