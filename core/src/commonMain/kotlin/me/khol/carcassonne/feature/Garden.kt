package me.khol.carcassonne.feature

import me.khol.carcassonne.PlacedFigure

data class Garden(
    val placedGarden: PlacedGarden,
    val neighborCount: Int,
    override val figures: List<PlacedFigure>,
) : Feature {

    override val placedElements = setOf(placedGarden)

    val isFinished: Boolean
        get() = neighborCount == 9

    override fun points(endGame: Boolean): Int? {
        if (!endGame && !isFinished) return null
        return neighborCount
    }

    override fun toString(): String = buildFeatureString("Garden") {
        withField("placedGarden", placedGarden)
        withField("isFinished", isFinished)
        withField("neighborCount", neighborCount)
    }
}
