package me.khol.carcassonne.figure

import me.khol.carcassonne.Player
import me.khol.carcassonne.feature.Feature

data object Pig : Figure {

    override fun presence(feature: Feature): Int = 0

    override fun canBePlaced(feature: Feature, player: Player): Boolean =
        feature is Feature.Field && feature.figures.any { it.figure.player == player }
}
