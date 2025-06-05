package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.ElementKey
import me.khol.carcassonne.surroundingCoordinates

fun Board.getMonasteryFeatures(): Set<Feature.Monastery> {
    val processedPlacedMonasteries: MutableMap<PlacedMonasteryGroup, Feature.Monastery> = mutableMapOf()

    tiles.forEach { (coordinates, tile) ->
        val tileMonasteries = tile.elements.get(ElementKey.Monastery)

        // There can be at most a single monastery on a tile
        tileMonasteries.forEach { monastery: ElementGroup.Center ->
            val placedMonastery = PlacedMonasteryGroup(coordinates = coordinates, elementGroup = monastery)

            val surroundingCoordinates = coordinates.surroundingCoordinates()
            val surroundingTiles = surroundingCoordinates.mapNotNull { tiles[it] }

            val monasteryFeature = Feature.Monastery(
                monastery = placedMonastery,
                neighborCount = surroundingTiles.count(),
            )
            processedPlacedMonasteries[placedMonastery] = monasteryFeature
        }
    }

    return processedPlacedMonasteries.values.toSet()
}
