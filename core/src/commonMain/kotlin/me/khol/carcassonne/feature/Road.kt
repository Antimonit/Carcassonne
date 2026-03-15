package me.khol.carcassonne.feature

import me.khol.carcassonne.Boon
import me.khol.carcassonne.PlacedFigure

data class Road(
    val placedRoads: Set<PlacedRoad>,
    val isFinished: Boolean,
    override val figures: List<PlacedFigure>,
) : Feature {

    override val placedElements = placedRoads

    val hasInn: Boolean
        get() = placedRoads.any { it.rotatedElement.element.boons.contains(Boon.Road.Inn) }

    override fun toString(): String = buildFeatureString("Road") {
        withField("placedRoads", placedRoads)
        withField("isFinished", isFinished)
        withField("figures", figures)
        withField("hasInn", hasInn)
    }
}
