package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element

fun Board.getFieldFeatures(): Set<Feature.Field> {
    val cityFeatures: Set<Feature.City> = getCityFeatures()

    val processedPlacedFields: MutableMap<PlacedField, Feature.Field> = mutableMapOf()

    tiles.forEach { (coordinates, tile) ->
        val tileFields = tile.elements[Element.Field]

        tileFields.forEach { field: Element.Field ->
            val placedField = PlacedField(coordinates = coordinates, element = field)

            if (placedField in processedPlacedFields)
                return@forEach

            val placedFields = mutableSetOf<PlacedField>()

            fun followFields(placedField: PlacedField) {
                if (placedField in placedFields)
                    return
                placedFields += placedField

                val neighbors = placedField.neighborElements(board = this, key = Element.Field)

                neighbors.filterNotNull().forEach(::followFields)
            }

            followFields(placedField)

            val connectedCities: List<Feature.City> = placedFields.flatMap { placedField: PlacedField ->
                cityFeatures.filter { cityFeature: Feature.City ->
                    cityFeature.placedCities.any { placedCity: PlacedCity ->
                        placedCity.coordinates == placedField.coordinates &&
                            placedCity.element in placedField.element.connectedCities
                    }
                }
            }

            val fieldFeature = Feature.Field(
                placedFields = placedFields,
                connectedCities = connectedCities.filter { it.isFinished }.toSet(),
            )
            placedFields.forEach { placedField: PlacedField ->
                processedPlacedFields[placedField] = fieldFeature
            }
        }
    }

    return processedPlacedFields.values.toSet()
}
