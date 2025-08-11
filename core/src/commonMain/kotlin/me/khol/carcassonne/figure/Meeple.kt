package me.khol.carcassonne.figure

import me.khol.carcassonne.Player
import me.khol.carcassonne.feature.Feature

data object Meeple : Figure {

    override fun presence(feature: Feature): Int = 1

    override fun canBePlaced(feature: Feature, player: Player): Boolean =
        feature.figures.isEmpty() && when (feature) {
            is Feature.Garden -> false
            is Feature.City -> feature.figures.isEmpty()
            is Feature.Field -> feature.figures.isEmpty()
            is Feature.Monastery -> feature.figures.isEmpty()
            is Feature.Road -> feature.figures.isEmpty()
        }
}





