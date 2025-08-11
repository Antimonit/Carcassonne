package me.khol.carcassonne.figure

import me.khol.carcassonne.feature.Feature

interface Figure {

    fun presence(feature: Feature): Int
}
