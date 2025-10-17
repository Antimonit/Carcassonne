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

        val tile: PlacedTile
        val validElements: Set<Element<*>>

        data class Fresh(
            override val tile: PlacedTile,
            override val validElements: Set<Element<*>>,
        ) : PlacingFigure, Undoable {

            init {
                val allElements = tile.rotatedTile.tile.elements.all()
                validElements.forEach {
                    check(it in allElements) { "Could not find $it in $allElements" }
                }
            }

            override fun undo() = PlacingTile.Placed(placedTile = tile)
        }

        data class Placed(
            override val tile: PlacedTile,
            override val validElements: Set<Element<*>>,
            val placedFigure: PlacedFigure,
        ) : PlacingFigure, Undoable {

            init {
                val allElements = tile.rotatedTile.tile.elements.all()
                validElements.forEach {
                    check(it in allElements) { "Could not find $it in $allElements" }
                }
            }

            override fun undo() = Fresh(tile = tile, validElements = validElements)
        }
    }

    data object Scoring : Phase

    data object FinalScoring : Phase
}