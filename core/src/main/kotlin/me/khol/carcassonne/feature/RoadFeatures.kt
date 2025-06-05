package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.ElementKey
import me.khol.carcassonne.ElementPosition
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.oppositeCoordinates
import me.khol.carcassonne.oppositeEdge

fun Board.getRoadFeatures(): Set<Feature.Road> {
    val processedPlacedRoadGroups: MutableMap<PlacedRoadGroup, Feature.Road> = mutableMapOf()

    tiles.forEach { (coordinates, tile) ->
        val tileRoads = tile.elements.get(ElementKey.Road)

        // A single tile may have multiple roads, which may or may not become connected
        // through other tiles. Process each road separately.
        tileRoads.forEach { road: ElementGroup.Road ->
            val placedRoad = PlacedRoadGroup(coordinates = coordinates, elementGroup = road)
            // if already processed, bail out
            if (placedRoad in processedPlacedRoadGroups)
                return@forEach

            val roadFeatures = mutableSetOf<PlacedRoadGroup>()
            var isFinished = true

            fun followRoads(placedRoad: PlacedRoadGroup) {
                if (placedRoad in roadFeatures)
                    return
                roadFeatures += placedRoad
                placedRoad.elementGroup.positions.forEach { roadEdge: ElementPosition.Edge ->
                    val otherCoordinates = placedRoad.coordinates.oppositeCoordinates(roadEdge)
                    val otherTile: RotatedTile? = tiles[otherCoordinates]
                    if (otherTile == null) {
                        // empty tile space, road is open-ended
                        isFinished = false
                    } else {
                        val otherEdge = roadEdge.oppositeEdge()
                        // it is guaranteed to have a road
                        val otherRoad: ElementGroup.Road = otherTile.elements.get(ElementKey.Road)
                            .first { otherTileRoad: ElementGroup.Road ->
                                otherEdge in otherTileRoad.positions
                            }
                        followRoads(PlacedRoadGroup(coordinates = otherCoordinates, elementGroup = otherRoad))
                    }
                }
            }

            followRoads(placedRoad)

            val roadFeature = Feature.Road(
                roads = roadFeatures,
                isFinished = isFinished,
            )
            roadFeatures.forEach { placedRoad: PlacedRoadGroup ->
                processedPlacedRoadGroups[placedRoad] = roadFeature
            }
        }
    }

    return processedPlacedRoadGroups.values.toSet()
}
