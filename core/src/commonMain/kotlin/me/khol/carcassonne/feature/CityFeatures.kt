package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element

fun Board.getCityFeatures(): Set<City> =
    getEdgeFeatures(Element.City, ::City)
