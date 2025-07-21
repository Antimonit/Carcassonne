package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementPosition
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.oppositeCoordinates
import me.khol.carcassonne.oppositeEdge
import kotlin.collections.forEach

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
                placedField.element.positions.forEach { fieldEdge: ElementPosition.SplitEdge ->
                    val otherCoordinates = placedField.coordinates.oppositeCoordinates(fieldEdge)
                    val otherTile: RotatedTile? = tiles[otherCoordinates]
                    if (otherTile != null) {
                        val otherEdge = fieldEdge.oppositeEdge()
                        // it is guaranteed to have a field
                        val otherField: Element.Field = otherTile.elements[Element.Field]
                            .first { otherTileField: Element.Field ->
                                otherEdge in otherTileField.positions
                            }
                        followFields(PlacedField(coordinates = otherCoordinates, element = otherField))
                    }
                }
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
