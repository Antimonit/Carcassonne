package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element

fun Board.getFieldFeatures(): Set<Feature.Field> {
    val cityFeatures: Set<Feature.City> = getCityFeatures()

    return getSplitEdgeFeatures(Element.Field) { placedFields, figures ->
        val connectedCities: List<Feature.City> = placedFields.flatMap { placedField: PlacedField ->
            cityFeatures.filter { cityFeature: Feature.City ->
                cityFeature.placedCities.any { placedCity: PlacedCity ->
                    placedCity.coordinates == placedField.coordinates &&
                        placedCity.element in placedField.element.connectedCities
                }
            }
        }

        Feature.Field(
            placedFields = placedFields,
            connectedCities = connectedCities.filter { it.isFinished }.toSet(),
            figures = figures,
        )
    }
}
