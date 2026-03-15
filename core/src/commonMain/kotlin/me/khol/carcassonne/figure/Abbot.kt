package me.khol.carcassonne.figure

import me.khol.carcassonne.Player
import me.khol.carcassonne.feature.Feature
import me.khol.carcassonne.feature.Garden
import me.khol.carcassonne.feature.Monastery

data object Abbot : Figure {

    override fun presence(feature: Feature): Int = 1

    override fun canBePlaced(feature: Feature, player: Player): Boolean =
        feature.figures.isEmpty() && (feature is Garden || feature is Monastery)
}
