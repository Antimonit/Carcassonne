package me.khol.carcassonne.feature

import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.ElementPosition

data class PlacedElementGroup<G : ElementGroup<out ElementPosition>>(
    val coordinates: Coordinates,
    val elementGroup: G,
)

typealias PlacedFieldGroup = PlacedElementGroup<ElementGroup.Field>
typealias PlacedCityGroup = PlacedElementGroup<ElementGroup.City>
typealias PlacedRoadGroup = PlacedElementGroup<ElementGroup.Road>
typealias PlacedMonasteryGroup = PlacedElementGroup<ElementGroup.Center>
typealias PlacedGardenGroup = PlacedElementGroup<ElementGroup.Center>

interface Feature {

    data class Field(
        val fields: Set<PlacedFieldGroup>,
    ) : Feature

    data class City(
        val cities: Set<PlacedCityGroup>,
    ) : Feature

    data class Road(
        val roads: Set<PlacedRoadGroup>,
    ) : Feature

    data class Monastery(
        val monastery: PlacedMonasteryGroup,
    ) : Feature

    data class Garden(
        val garden: PlacedGardenGroup,
    ) : Feature
}
