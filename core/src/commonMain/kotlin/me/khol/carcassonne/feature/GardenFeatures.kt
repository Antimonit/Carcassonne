package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element

fun Board.getGardenFeatures(): Set<Garden> =
    getCenterFeatures(Element.Garden, ::Garden)
