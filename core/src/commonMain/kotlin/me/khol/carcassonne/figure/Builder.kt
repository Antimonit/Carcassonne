package me.khol.carcassonne.figure

import me.khol.carcassonne.Player
import me.khol.carcassonne.feature.City
import me.khol.carcassonne.feature.Feature
import me.khol.carcassonne.feature.Field
import me.khol.carcassonne.feature.Garden
import me.khol.carcassonne.feature.Monastery
import me.khol.carcassonne.feature.Road

data object Builder : Figure {

    override fun presence(feature: Feature): Int = 0

    override fun canBePlaced(feature: Feature, player: Player): Boolean =
        when (feature) {
            is City -> feature.figures.any { it.figure.player == player }
            is Field -> false
            is Garden -> false
            is Monastery -> false
            is Road -> feature.figures.any { it.figure.player == player }
        }
}
