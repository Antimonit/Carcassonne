package me.khol.carcassonne.feature

import me.khol.carcassonne.PlacedFigure

data class Field(
    val placedFields: Set<PlacedField>,
    val connectedCities: Set<City>,
    override val figures: List<PlacedFigure>,
) : Feature {

    override val placedElements = placedFields

    override fun points(endGame: Boolean): Int? {
        if (!endGame) return null
        return connectedCities.count { it.isFinished } * 3
    }

    override fun toString(): String = buildFeatureString("Field") {
        withField("placedFields", placedFields)
        withField("connectedCities", connectedCities)
        withField("figures", figures)
    }
}
