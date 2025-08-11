package me.khol.carcassonne.figure

import me.khol.carcassonne.feature.Feature

data object Mayor : Figure {

    override fun presence(feature: Feature): Int {
        if (feature is Feature.City) {
            return feature.coatOfArms
        }
        return 0
    }
}
