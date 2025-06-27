package me.khol.carcassonne

sealed interface Phase {

    sealed interface PlacingTile : Phase {

        val tile: Tile

        data class Fresh(
            override val tile: Tile,
        ) : PlacingTile

        data class Placed(
            val placedTile: PlacedTile,
        ) : PlacingTile {
            
            override val tile: Tile = placedTile.rotatedTile.tile
        }
    }

    sealed interface PlacingMeeple : Phase {

        val tile: PlacedTile

        data class Fresh(
            override val tile: PlacedTile,
        ) : PlacingMeeple

        data class Placed(
            override val tile: PlacedTile,
            val element: Element<*>,
        ) : PlacingMeeple
    }

    data object Scoring : Phase

    data object FinalScoring : Phase
}