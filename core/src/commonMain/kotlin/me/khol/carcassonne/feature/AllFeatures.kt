package me.khol.carcassonne.feature

import me.khol.carcassonne.Board

fun Board.getAllFeatures(): Set<Feature> =
    getCityFeatures() +
        getRoadFeatures() +
        getFieldFeatures() +
        getMonasteryFeatures() +
        getGardenFeatures()