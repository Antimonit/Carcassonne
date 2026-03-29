package me.khol.carcassonne

sealed interface Phase {

    interface Undoable {

        fun undo(): Phase
    }

    sealed interface PlacingTile : Phase {

        val tile: Tile

        data class Fresh(
            override val tile: Tile,
        ) : PlacingTile

        data class Placed(
            val placedTile: PlacedTile,
        ) : PlacingTile, Undoable {

            override val tile: Tile = placedTile.rotatedTile.tile

            override fun undo() = Fresh(tile = tile)
        }
    }

    data class PlacingFigure(
        val placedTile: PlacedTile,
        val validFigurePlacements: Map<RotatedElement<*>, List<PlacedFigure>>,
        val selectedFigure: PlacedFigure? = null,
    ) : Phase, Undoable {

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
            if (selectedFigure != null) {
                copy(selectedFigure = null)
            } else {
                PlacingTile.Placed(placedTile = placedTile)
            }
    }

    data class Scoring(
        val scoringEvent: History.Event.Scoring,
    ) : Phase

    data object FinalScoring : Phase
}