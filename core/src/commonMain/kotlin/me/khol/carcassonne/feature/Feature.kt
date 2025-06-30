package me.khol.carcassonne.feature

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementPosition

data class PlacedElement<E : Element<out ElementPosition>>(
    val coordinates: Coordinates,
    val element: E,
)

typealias PlacedField = PlacedElement<Element.Field>
typealias PlacedCity = PlacedElement<Element.City>
typealias PlacedRoad = PlacedElement<Element.Road>
typealias PlacedMonastery = PlacedElement<Element.Monastery>
typealias PlacedGarden = PlacedElement<Element.Garden>

interface Feature {

    data class Field(
        val fields: Set<PlacedField>,
        val connectedCities: Set<City>,
    ) : Feature

    data class City(
        val cities: Set<PlacedCity>,
        val isFinished: Boolean,
    ) : Feature {

        val hasCathedral: Boolean
            get() = cities.any { it.element.boons.contains(Boon.City.Cathedral) }
        val coatOfArms: Int
            get() = cities.count { it.element.boons.contains(Boon.City.CoatOfArms) }
    }

    data class Road(
        val roads: Set<PlacedRoad>,
        val isFinished: Boolean,
    ) : Feature {

        val hasInn: Boolean
            get() = roads.any { it.element.boons.contains(Boon.Road.Inn) }
    }

    data class Monastery(
        val monastery: PlacedMonastery,
        val neighborCount: Int,
    ) : Feature {

        val isFinished: Boolean
            get() = neighborCount == 9
    }

    data class Garden(
        val garden: PlacedGarden,
        val neighborCount: Int,
    ) : Feature {

        val isFinished: Boolean
            get() = neighborCount == 9
    }
}
