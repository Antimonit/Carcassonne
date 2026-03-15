package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element

fun Board.getMonasteryFeatures(): Set<Monastery> =
    getCenterFeatures(Element.Monastery, ::Monastery)
