package me.khol.carcassonne.feature

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementPosition
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.RotatedElement

data class PlacedElement<E : Element<ElementPosition>>(
    val coordinates: Coordinates,
    val rotatedElement: RotatedElement<E>,
)

fun <E : Element<ElementPosition>> RotatedElement<E>.placed(coordinates: Coordinates) =
    PlacedElement(rotatedElement = this, coordinates = coordinates)

fun <E : Element<ElementPosition>> RotatedElement<E>.placed(x: Int, y: Int) =
    placed(coordinates = Coordinates(x, y))

typealias PlacedField = PlacedElement<Element.Field>
typealias PlacedCity = PlacedElement<Element.City>
typealias PlacedRoad = PlacedElement<Element.Road>
typealias PlacedMonastery = PlacedElement<Element.Monastery>
typealias PlacedGarden = PlacedElement<Element.Garden>

sealed interface Feature {

    val placedElements: Set<PlacedElement<*>>
    val figures: List<PlacedFigure>

    data class Field(
        val placedFields: Set<PlacedField>,
        val connectedCities: Set<City>,
        override val figures: List<PlacedFigure>,
    ) : Feature {
        override val placedElements = placedFields

        override fun toString(): String = buildFeatureString("Field") {
            withField("placedFields", placedFields)
            withField("connectedCities", connectedCities)
            withField("figures", figures)
        }
    }

    data class City(
        val placedCities: Set<PlacedCity>,
        val isFinished: Boolean,
        override val figures: List<PlacedFigure>,
    ) : Feature {
        override val placedElements = placedCities

        val hasCathedral: Boolean
            get() = placedCities.any { it.rotatedElement.element.boons.contains(Boon.City.Cathedral) }
        val coatOfArms: Int
            get() = placedCities.count { it.rotatedElement.element.boons.contains(Boon.City.CoatOfArms) }

        override fun toString(): String = buildFeatureString("City") {
            withField("placedCities", placedCities)
            withField("isFinished", isFinished)
            withField("figures", figures)
            withField("hasCathedral", hasCathedral)
            withField("coatOfArms", coatOfArms)
        }
    }

    data class Road(
        val placedRoads: Set<PlacedRoad>,
        val isFinished: Boolean,
        override val figures: List<PlacedFigure>,
    ) : Feature {
        override val placedElements = placedRoads

        val hasInn: Boolean
            get() = placedRoads.any { it.rotatedElement.element.boons.contains(Boon.Road.Inn) }

        override fun toString(): String = buildFeatureString("Road") {
            withField("placedRoads", placedRoads)
            withField("isFinished", isFinished)
            withField("figures", figures)
            withField("hasInn", hasInn)
        }
    }

    data class Monastery(
        val placedMonastery: PlacedMonastery,
        val neighborCount: Int,
        override val figures: List<PlacedFigure>,
    ) : Feature {
        override val placedElements = setOf(placedMonastery)

        val isFinished: Boolean
            get() = neighborCount == 9

        override fun toString(): String = buildFeatureString("Monastery") {
            withField("placedMonastery", placedMonastery)
            withField("isFinished", isFinished)
            withField("neighborCount", neighborCount)
        }
    }

    data class Garden(
        val placedGarden: PlacedGarden,
        val neighborCount: Int,
        override val figures: List<PlacedFigure>,
    ) : Feature {
        override val placedElements = setOf(placedGarden)

        val isFinished: Boolean
            get() = neighborCount == 9

        override fun toString(): String = buildFeatureString("Garden") {
            withField("placedGarden", placedGarden)
            withField("isFinished", isFinished)
            withField("neighborCount", neighborCount)
        }
    }
}

infix operator fun Feature.contains(placedFigure : PlacedFigure): Boolean =
    placedFigure.placedElement in placedElements