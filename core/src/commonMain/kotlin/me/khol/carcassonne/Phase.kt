package me.khol.carcassonne

sealed interface Phase {

    interface Undoable {

        fun undo(): Phase
    }

    data class PlacingTile(
        val tile: Tile,
        val validTilePlacements: Map<Coordinates, List<PlacedTile>>,
        val placedTile: PlacedTile? = null,
    ) : Phase, Undoable {

        init {
            check(placedTile == null || placedTile in validTilePlacements.getValue(placedTile.coordinates)) {
                "Can not place $tile as $placedTile. Can place only as $validTilePlacements."
            }
        }

        override fun undo(): Phase = copy(placedTile = null)
    }

    data class PlacingFigure(
        val placedTile: PlacedTile,
        val validTilePlacements: Map<Coordinates, List<PlacedTile>>,
        val validFigurePlacements: Map<RotatedElement<*>, List<PlacedFigure>>,
        val placedFigure: PlacedFigure? = null,
        val confirmationActions: List<ConfirmationAction>,
    ) : Phase, Undoable {

        sealed interface ConfirmationAction {

            data class ScoreAbbot(
                val figure: PlacedFigure,
            ) : ConfirmationAction
        }

        init {
            val allElements = placedTile.rotatedTile.rotatedElements.all()
            check(validFigurePlacements.size == allElements.size) {
                "Element count in validElements (${validFigurePlacements.size}) and allElements (${allElements.size}) do not match."
            }
            check(validFigurePlacements.keys == allElements.toSet()) {
                "Elements in validFigurePlacements and allElements do not match."
            }
        }

        override fun undo(): Phase =
            if (placedFigure != null) {
                copy(placedFigure = null)
            } else {
                PlacingTile(
                    tile = placedTile.rotatedTile.tile,
                    validTilePlacements = validTilePlacements,
                    placedTile = placedTile,
                )
            }
    }

    data class Scoring(
        val scoringEvent: History.Event.Scoring,
    ) : Phase

    data object FinalScoring : Phase
}