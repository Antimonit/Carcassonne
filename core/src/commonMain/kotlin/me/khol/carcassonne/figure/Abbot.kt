package me.khol.carcassonne.figure

import me.khol.carcassonne.feature.Feature

object Abbot : Figure {

    override fun presence(feature: Feature): Int = 1
}
