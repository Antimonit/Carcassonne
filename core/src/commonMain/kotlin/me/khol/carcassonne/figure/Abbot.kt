package me.khol.carcassonne.figure

import me.khol.carcassonne.Player
import me.khol.carcassonne.feature.Feature

data object Abbot : Figure {

    override fun presence(feature: Feature): Int = 1

    override fun canBePlaced(feature: Feature, player: Player): Boolean =
        feature.figures.isEmpty() && (feature is Feature.Garden || feature is Feature.Monastery)
}
