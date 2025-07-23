package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element

fun Board.getRoadFeatures(): Set<Feature.Road> {
    val processedPlacedRoads: MutableMap<PlacedRoad, Feature.Road> = mutableMapOf()

    tiles.forEach { (coordinates, tile) ->
        val tileRoads = tile.elements[Element.Road]

        // A single tile may have multiple roads, which may or may not become connected
        // through other tiles. Process each road separately.
        tileRoads.forEach { road: Element.Road ->
            val placedRoad = PlacedRoad(coordinates = coordinates, element = road)
            // if already processed, bail out
            if (placedRoad in processedPlacedRoads)
                return@forEach

            val roadFeatures = mutableSetOf<PlacedRoad>()
            var isFinished = true

            fun followRoads(placedRoad: PlacedRoad) {
                if (placedRoad in roadFeatures)
                    return
                roadFeatures += placedRoad

                val neighbors = placedRoad.neighborElements(board = this, key = Element.Road)

                if (neighbors.contains(null)) {
                    isFinished = false
                }

                neighbors.filterNotNull().forEach(::followRoads)
            }

            followRoads(placedRoad)

            val roadFeature = Feature.Road(
                placedRoads = roadFeatures,
                isFinished = isFinished,
            )
            roadFeatures.forEach { placedRoad: PlacedRoad ->
                processedPlacedRoads[placedRoad] = roadFeature
            }
        }
    }

    return processedPlacedRoads.values.toSet()
}
