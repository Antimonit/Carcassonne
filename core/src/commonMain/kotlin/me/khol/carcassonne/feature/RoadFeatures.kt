package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementPosition
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.oppositeCoordinates
import me.khol.carcassonne.oppositeEdge

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
                placedRoad.element.positions.forEach { roadEdge: ElementPosition.Edge ->
                    val otherCoordinates = placedRoad.coordinates.oppositeCoordinates(roadEdge)
                    val otherTile: RotatedTile? = tiles[otherCoordinates]
                    if (otherTile == null) {
                        // empty tile space, road is open-ended
                        isFinished = false
                    } else {
                        val otherEdge = roadEdge.oppositeEdge()
                        // it is guaranteed to have a road
                        val otherRoad: Element.Road = otherTile.elements[Element.Road]
                            .first { otherTileRoad: Element.Road ->
                                otherEdge in otherTileRoad.positions
                            }
                        followRoads(PlacedRoad(coordinates = otherCoordinates, element = otherRoad))
                    }
                }
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
