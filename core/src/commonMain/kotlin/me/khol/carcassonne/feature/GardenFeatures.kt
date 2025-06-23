package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.ElementKey
import me.khol.carcassonne.surroundingCoordinates

fun Board.getGardenFeatures(): Set<Feature.Garden> {
    val processedPlacedGardens: MutableMap<PlacedGardenGroup, Feature.Garden> = mutableMapOf()

    tiles.forEach { (coordinates, tile) ->
        val tileGardens = tile.elements.get(ElementKey.Garden)

        // There can be at most a single garden on a tile
        tileGardens.forEach { garden: ElementGroup.Garden ->
            val placedGarden = PlacedGardenGroup(coordinates = coordinates, elementGroup = garden)

            val surroundingCoordinates = coordinates.surroundingCoordinates()
            val surroundingTiles = surroundingCoordinates.mapNotNull { tiles[it] }

            val gardenFeature = Feature.Garden(
                garden = placedGarden,
                neighborCount = surroundingTiles.count(),
            )
            processedPlacedGardens[placedGarden] = gardenFeature
        }
    }

    return processedPlacedGardens.values.toSet()
}
