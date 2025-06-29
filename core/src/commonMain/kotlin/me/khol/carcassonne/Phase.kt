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

        data class Fresh(
            override val tile: PlacedTile,
        ) : PlacingFigure, Undoable {

            override fun undo() = PlacingTile.Placed(placedTile = tile)
        }

        data class Placed(
            override val tile: PlacedTile,
            val element: Element<*>,
        ) : PlacingFigure, Undoable {

            override fun undo() = Fresh(tile = tile)
        }
    }

    data object Scoring : Phase

    data object FinalScoring : Phase
}