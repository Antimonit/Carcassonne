package me.khol.carcassonne.feature

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementPosition

data class PlacedElement<E : Element<ElementPosition>>(
    val coordinates: Coordinates,
    // Already rotated element
    val element: E,
)

typealias PlacedField = PlacedElement<Element.Field>
typealias PlacedCity = PlacedElement<Element.City>
typealias PlacedRoad = PlacedElement<Element.Road>
typealias PlacedMonastery = PlacedElement<Element.Monastery>
typealias PlacedGarden = PlacedElement<Element.Garden>

interface Feature {

    data class Field(
        val placedFields: Set<PlacedField>,
        val connectedCities: Set<City>,
    ) : Feature

    data class City(
        val placedCities: Set<PlacedCity>,
        val isFinished: Boolean,
    ) : Feature {

        val hasCathedral: Boolean
            get() = placedCities.any { it.element.boons.contains(Boon.City.Cathedral) }
        val coatOfArms: Int
            get() = placedCities.count { it.element.boons.contains(Boon.City.CoatOfArms) }
    }

    data class Road(
        val placedRoads: Set<PlacedRoad>,
        val isFinished: Boolean,
    ) : Feature {

        val hasInn: Boolean
            get() = placedRoads.any { it.element.boons.contains(Boon.Road.Inn) }
    }

    data class Monastery(
        val placedMonastery: PlacedMonastery,
        val neighborCount: Int,
    ) : Feature {

        val isFinished: Boolean
            get() = neighborCount == 9
    }

    data class Garden(
        val placedGarden: PlacedGarden,
        val neighborCount: Int,
    ) : Feature {

        val isFinished: Boolean
            get() = neighborCount == 9
    }
}
