package me.khol.carcassonne.figure

import me.khol.carcassonne.Player
import me.khol.carcassonne.feature.Feature

data object Builder : Figure {

    override fun presence(feature: Feature): Int = 0

    override fun canBePlaced(feature: Feature, player: Player): Boolean =
        when (feature) {
            is Feature.City -> feature.figures.any { it.figure.player == player }
            is Feature.Field -> false
            is Feature.Garden -> false
            is Feature.Monastery -> false
            is Feature.Road -> feature.figures.any { it.figure.player == player }
        }
}
