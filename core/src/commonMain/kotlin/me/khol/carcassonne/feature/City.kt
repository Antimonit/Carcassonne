package me.khol.carcassonne.feature

import me.khol.carcassonne.Boon
import me.khol.carcassonne.PlacedFigure

data class City(
    val placedCities: Set<PlacedCity>,
    val isFinished: Boolean,
    override val figures: List<PlacedFigure>,
) : Feature {

    override val placedElements = placedCities

    val hasCathedral: Boolean
        get() = placedCities.any { it.rotatedElement.element.boons.contains(Boon.City.Cathedral) }
    val coatOfArms: Int
        get() = placedCities.count { it.rotatedElement.element.boons.contains(Boon.City.CoatOfArms) }

    override fun toString(): String = buildFeatureString("City") {
        withField("placedCities", placedCities)
        withField("isFinished", isFinished)
        withField("figures", figures)
        withField("hasCathedral", hasCathedral)
        withField("coatOfArms", coatOfArms)
    }
}
