package me.khol.carcassonne

import me.khol.carcassonne.feature.PlacedElement

data class PlacedFigure(
    val placedElement: PlacedElement<*>,
    val figure: PlayerFigure,
)
