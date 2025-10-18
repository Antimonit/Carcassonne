package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element

fun Board.getCityFeatures(): Set<Feature.City> =
    getEdgeFeatures(Element.City, Feature::City)
