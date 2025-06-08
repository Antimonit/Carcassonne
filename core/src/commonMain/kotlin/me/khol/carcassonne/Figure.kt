package me.khol.carcassonne

import me.khol.carcassonne.feature.PlacedElementGroup

// https://wikicarpedia.com/car/Game_Figures
enum class Figure {
    Meeple,
    Abbot,
    Builder,
    Pig,
}

data class PlacedFigure<G : ElementGroup<out ElementPosition>>(
    val figure: Figure,
    val placedFeature: PlacedElementGroup<G>,
)
