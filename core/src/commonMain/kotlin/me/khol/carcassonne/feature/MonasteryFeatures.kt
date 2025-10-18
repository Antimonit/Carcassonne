package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element

fun Board.getMonasteryFeatures(): Set<Feature.Monastery> =
    getCenterFeatures(Element.Monastery, Feature::Monastery)
