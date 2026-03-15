package me.khol.carcassonne.feature

import me.khol.carcassonne.PlacedFigure

data class Monastery(
    val placedMonastery: PlacedMonastery,
    val neighborCount: Int,
    override val figures: List<PlacedFigure>,
) : Feature {

    override val placedElements = setOf(placedMonastery)

    val isFinished: Boolean
        get() = neighborCount == 9

    override fun points(endGame: Boolean): Int? {
        if (!endGame && !isFinished) return null
        return neighborCount
    }

    override fun toString(): String = buildFeatureString("Monastery") {
        withField("placedMonastery", placedMonastery)
        withField("isFinished", isFinished)
        withField("neighborCount", neighborCount)
    }
}
