package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element
import me.khol.carcassonne.surroundingCoordinates

fun Board.getGardenFeatures(): Set<Feature.Garden> {
    val processedPlacedGardens: MutableMap<PlacedGarden, Feature.Garden> = mutableMapOf()

    tiles.forEach { (coordinates, tile) ->
        val tileGardens = tile.elements[Element.Garden]

        // There can be at most a single garden on a tile
        tileGardens.forEach { garden: Element.Garden ->
            val placedGarden = PlacedGarden(coordinates = coordinates, element = garden)

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
