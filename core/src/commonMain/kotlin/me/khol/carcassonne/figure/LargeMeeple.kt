package me.khol.carcassonne.figure

import me.khol.carcassonne.Player
import me.khol.carcassonne.feature.Feature

data object LargeMeeple : Figure {

    override fun presence(feature: Feature): Int = 2

    override fun canBePlaced(feature: Feature, player: Player): Boolean =
        Meeple.canBePlaced(feature, player)
}
