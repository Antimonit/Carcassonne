package me.khol.carcassonne.feature

import me.khol.carcassonne.Boon
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
        val connectedCities: Set<City>,
    ) : Feature

    data class City(
        val cities: Set<PlacedCityGroup>,
        val isFinished: Boolean,
    ) : Feature {

        val hasCathedral: Boolean
            get() = cities.any { it.elementGroup.boons.contains(Boon.City.Cathedral) }
        val coatOfArms: Int
            get() = cities.count { it.elementGroup.boons.contains(Boon.City.CoatOfArms) }
    }

    data class Road(
        val roads: Set<PlacedRoadGroup>,
        val isFinished: Boolean,
    ) : Feature {

        val hasInn: Boolean
            get() = roads.any { it.elementGroup.boons.contains(Boon.Road.Inn) }
    }

    data class Monastery(
        val monastery: PlacedMonasteryGroup,
        val neighborCount: Int,
    ) : Feature {

        val isFinished: Boolean
            get() = neighborCount == 9
    }

    data class Garden(
        val garden: PlacedGardenGroup,
    ) : Feature
}
