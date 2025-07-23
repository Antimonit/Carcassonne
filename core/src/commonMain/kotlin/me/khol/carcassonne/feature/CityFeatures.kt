package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element

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

                val neighbors = placedCity.neighborElements(board = this, key = Element.City)

                if (neighbors.contains(null)) {
                    isFinished = false
                }

                neighbors.filterNotNull().forEach(::followCities)
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
