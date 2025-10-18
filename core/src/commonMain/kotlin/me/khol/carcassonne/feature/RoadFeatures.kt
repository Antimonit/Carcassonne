package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element

fun Board.getRoadFeatures(): Set<Feature.Road> =
    getEdgeFeatures(Element.Road, Feature::Road)
