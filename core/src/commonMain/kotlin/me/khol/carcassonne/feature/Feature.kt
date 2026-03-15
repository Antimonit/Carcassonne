package me.khol.carcassonne.feature

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

    /**
     * Calculate how many points a feature would be worth when scored.
     *
     * A null return value signifies the feature is not scorable yet.
     */
    fun points(endGame: Boolean): Int?
}
