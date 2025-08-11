package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.PlayerFigure

fun Board.getFiguresFromFeature(
    placedElements: Collection<PlacedElement<*>>
): List<PlayerFigure> = placedElements
    .flatMap { getFigures(it) }
    .map { it.figure }
