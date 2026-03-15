package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element

fun Board.getRoadFeatures(): Set<Road> =
    getEdgeFeatures(Element.Road, ::Road)
