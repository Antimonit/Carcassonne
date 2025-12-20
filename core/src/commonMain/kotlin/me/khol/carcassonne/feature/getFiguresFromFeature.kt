package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.PlacedFigure

fun Board.getFiguresFromFeature(
    placedElements: Collection<PlacedElement<*>>
): List<PlacedFigure> = placedElements
    .flatMap { getFigures(it) }
