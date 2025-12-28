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

    sealed interface PlacingFigure : Phase {

        val placedTile: PlacedTile
        val validFigurePlacements: Map<Element<*>, List<PlacedFigure>>

        data class Fresh(
            override val placedTile: PlacedTile,
            override val validFigurePlacements: Map<Element<*>, List<PlacedFigure>>,
        ) : PlacingFigure, Undoable {

            init {
                val allElements = placedTile.rotatedTile.asTile().elements.all()
                check(validFigurePlacements.size == allElements.size) {
                    "Element count in validElements (${validFigurePlacements.size}) and allElements (${allElements.size}) do not match."
                }
                check(validFigurePlacements.keys == allElements.toSet()) {
                    "Elements in validFigurePlacements and allElements do not match."
                }
            }

            override fun undo() = PlacingTile.Placed(placedTile = placedTile)
        }

        data class Placed(
            override val placedTile: PlacedTile,
            override val validFigurePlacements: Map<Element<*>, List<PlacedFigure>>,
            val placedFigure: PlacedFigure,
        ) : PlacingFigure, Undoable {

            init {
                val allElements = placedTile.rotatedTile.asTile().elements.all()
                check(validFigurePlacements.size == allElements.size) {
                    "Element count in validElements (${validFigurePlacements.size}) and allElements (${allElements.size}) do not match."
                }
                check(validFigurePlacements.keys == allElements.toSet()) {
                    "Elements in validFigurePlacements and allElements do not match."
                }
            }

            override fun undo() = Fresh(placedTile = placedTile, validFigurePlacements = validFigurePlacements)
        }
    }

    data object Scoring : Phase

    data object FinalScoring : Phase
}