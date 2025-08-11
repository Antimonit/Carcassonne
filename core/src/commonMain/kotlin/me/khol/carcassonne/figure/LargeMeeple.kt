package me.khol.carcassonne.figure

import me.khol.carcassonne.feature.Feature

data object LargeMeeple : Figure {

    override fun presence(feature: Feature): Int = 2
}
