package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.ElementKey
import me.khol.carcassonne.ElementPosition
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.oppositeCoordinates
import me.khol.carcassonne.oppositeEdge

fun Board.getCityFeatures(): Set<Feature.City> {
    val processedPlacedCityGroups: MutableMap<PlacedCityGroup, Feature.City> = mutableMapOf()

    tiles.forEach { (coordinates, tile) ->
        val tileCities = tile.elements.get(ElementKey.City)

        tileCities.forEach { city: ElementGroup.City ->
            val placedCity = PlacedCityGroup(coordinates = coordinates, elementGroup = city)

            if (placedCity in processedPlacedCityGroups)
                return@forEach

            val placedCities = mutableSetOf<PlacedCityGroup>()
            var isFinished = true

            fun followCities(placedCity: PlacedCityGroup) {
                if (placedCity in placedCities)
                    return
                placedCities += placedCity
                placedCity.elementGroup.positions.forEach { cityEdge: ElementPosition.Edge ->
                    val otherCoordinates = placedCity.coordinates.oppositeCoordinates(cityEdge)
                    val otherTile: RotatedTile? = tiles[otherCoordinates]
                    if (otherTile == null) {
                        // empty tile space, city is not yet closed
                        isFinished = false
                    } else {
                        val otherEdge = cityEdge.oppositeEdge()
                        // it is guaranteed to have a city
                        val otherCity: ElementGroup.City = otherTile.elements.get(ElementKey.City)
                            .first { otherTileCity: ElementGroup.City ->
                                otherEdge in otherTileCity.positions
                            }
                        followCities(PlacedCityGroup(coordinates = otherCoordinates, elementGroup = otherCity))
                    }
                }
            }

            followCities(placedCity)

            val cityFeature = Feature.City(
                cities = placedCities,
                isFinished = isFinished,
            )
            placedCities.forEach { placedCity: PlacedCityGroup ->
                processedPlacedCityGroups[placedCity] = cityFeature
            }
        }
    }

    return processedPlacedCityGroups.values.toSet()
}
