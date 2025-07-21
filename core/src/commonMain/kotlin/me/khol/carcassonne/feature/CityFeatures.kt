package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementPosition
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.oppositeCoordinates
import me.khol.carcassonne.oppositeEdge

fun Board.getCityFeatures(): Set<Feature.City> {
    val processedPlacedCities: MutableMap<PlacedCity, Feature.City> = mutableMapOf()

    tiles.forEach { (coordinates, tile) ->
        val tileCities = tile.elements[Element.City]

        tileCities.forEach { city: Element.City ->
            val placedCity = PlacedCity(coordinates = coordinates, element = city)

            if (placedCity in processedPlacedCities)
                return@forEach

            val placedCities = mutableSetOf<PlacedCity>()
            var isFinished = true

            fun followCities(placedCity: PlacedCity) {
                if (placedCity in placedCities)
                    return
                placedCities += placedCity
                placedCity.element.positions.forEach { cityEdge: ElementPosition.Edge ->
                    val otherCoordinates = placedCity.coordinates.oppositeCoordinates(cityEdge)
                    val otherTile: RotatedTile? = tiles[otherCoordinates]
                    if (otherTile == null) {
                        // empty tile space, city is not yet closed
                        isFinished = false
                    } else {
                        val otherEdge = cityEdge.oppositeEdge()
                        // it is guaranteed to have a city
                        val otherCity: Element.City = otherTile.elements[Element.City]
                            .first { otherTileCity: Element.City ->
                                otherEdge in otherTileCity.positions
                            }
                        followCities(PlacedCity(coordinates = otherCoordinates, element = otherCity))
                    }
                }
            }

            followCities(placedCity)

            val cityFeature = Feature.City(
                placedCities = placedCities,
                isFinished = isFinished,
            )
            placedCities.forEach { placedCity: PlacedCity ->
                processedPlacedCities[placedCity] = cityFeature
            }
        }
    }

    return processedPlacedCities.values.toSet()
}
