package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element
import me.khol.carcassonne.surroundingCoordinates

fun Board.getMonasteryFeatures(): Set<Feature.Monastery> {
    val processedPlacedMonasteries: MutableMap<PlacedMonastery, Feature.Monastery> = mutableMapOf()

    tiles.forEach { (coordinates, tile) ->
        val tileMonasteries = tile.elements[Element.Monastery]

        // There can be at most a single monastery on a tile
        tileMonasteries.forEach { monastery: Element.Monastery ->
            val placedMonastery = PlacedMonastery(coordinates = coordinates, element = monastery)

            val surroundingCoordinates = coordinates.surroundingCoordinates()
            val surroundingTiles = surroundingCoordinates.mapNotNull { tiles[it] }

            val monasteryFeature = Feature.Monastery(
                placedMonastery = placedMonastery,
                neighborCount = surroundingTiles.count(),
            )
            processedPlacedMonasteries[placedMonastery] = monasteryFeature
        }
    }

    return processedPlacedMonasteries.values.toSet()
}
