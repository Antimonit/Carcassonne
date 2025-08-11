package me.khol.carcassonne.figure

import me.khol.carcassonne.feature.Feature

object Builder : Figure {

    override fun presence(feature: Feature): Int = 0
}
