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

    override fun points(endGame: Boolean): Int? {
        if (!endGame && !isFinished) return null
        val size = placedRoads.map { it.coordinates }.toSet().size
        val tileModifier = if (hasInn && endGame) 0 else 1
        return size * tileModifier
    }

    override fun toString(): String = buildFeatureString("Road") {
        withField("placedRoads", placedRoads)
        withField("isFinished", isFinished)
        withField("figures", figures)
        withField("hasInn", hasInn)
    }
}
