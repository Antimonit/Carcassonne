package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.ElementKey
import me.khol.carcassonne.ElementPosition
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.oppositeCoordinates
import me.khol.carcassonne.oppositeEdge
import kotlin.collections.forEach

fun Board.getFieldFeatures(): Set<Feature.Field> {
    val cityFeatures: Set<Feature.City> = getCityFeatures()

    val processedPlacedFields: MutableMap<PlacedFieldGroup, Feature.Field> = mutableMapOf()

    tiles.forEach { (coordinates, tile) ->
        val tileFields = tile.elements.get(ElementKey.Field)

        tileFields.forEach { field: ElementGroup.Field ->
            val placedField = PlacedFieldGroup(coordinates = coordinates, elementGroup = field)

            if (placedField in processedPlacedFields)
                return@forEach

            val placedFields = mutableSetOf<PlacedFieldGroup>()

            fun followFields(placedField: PlacedFieldGroup) {
                if (placedField in placedFields)
                    return
                placedFields += placedField
                placedField.elementGroup.positions.forEach { fieldEdge: ElementPosition.SplitEdge ->
                    val otherCoordinates = placedField.coordinates.oppositeCoordinates(fieldEdge)
                    val otherTile: RotatedTile? = tiles[otherCoordinates]
                    if (otherTile != null) {
                        val otherEdge = fieldEdge.oppositeEdge()
                        // it is guaranteed to have a field
                        val otherField: ElementGroup.Field = otherTile.elements.get(ElementKey.Field)
                            .first { otherTileField: ElementGroup.Field ->
                                otherEdge in otherTileField.positions
                            }
                        followFields(PlacedFieldGroup(coordinates = otherCoordinates, elementGroup = otherField))
                    }
                }
            }

            followFields(placedField)

            val connectedCities: List<Feature.City> = placedFields.flatMap { placedField: PlacedFieldGroup ->
                cityFeatures.filter { cityFeature: Feature.City ->
                    cityFeature.cities.any { placedCity: PlacedCityGroup ->
                        placedCity.coordinates == placedField.coordinates &&
                            placedCity.elementGroup in placedField.elementGroup.connectedCities
                    }
                }
            }

            val fieldFeature = Feature.Field(
                fields = placedFields,
                connectedCities = connectedCities.filter { it.isFinished }.toSet(),
            )
            placedFields.forEach { placedField: PlacedFieldGroup ->
                processedPlacedFields[placedField] = fieldFeature
            }
        }
    }

    return processedPlacedFields.values.toSet()
}
