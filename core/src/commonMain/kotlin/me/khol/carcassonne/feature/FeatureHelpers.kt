package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementKey
import me.khol.carcassonne.ElementPosition
import me.khol.carcassonne.PlayerFigure
import me.khol.carcassonne.surroundingCoordinates

/**
 * Generic helper for features that are built by following connected edge elements (City, Road).
 */
fun <E : Element<ElementPosition.Edge>, F : Feature> Board.getEdgeFeatures(
    key: ElementKey<E>,
    createFeature: (placedElements: Set<PlacedElement<E>>, isFinished: Boolean, figures: List<PlayerFigure>) -> F,
): Set<F> {
    val processedElements: MutableMap<PlacedElement<E>, F> = mutableMapOf()

    tiles.forEach { (coordinates, tile) ->
        val tileElements = tile.elements[key]

        tileElements.forEach { element: E ->
            val placedElement = PlacedElement(coordinates = coordinates, element = element)

            if (placedElement in processedElements)
                return@forEach

            val placedElements = mutableSetOf<PlacedElement<E>>()
            var isFinished = true

            fun follow(current: PlacedElement<E>) {
                if (current in placedElements)
                    return
                placedElements += current

                val neighbors = current.neighborElements(tiles = tiles, key = key)

                if (neighbors.contains(null)) {
                    isFinished = false
                }

                neighbors.filterNotNull().forEach(::follow)
            }

            follow(placedElement)

            val figures = getFiguresFromFeature(listOf(placedElement))

            val feature = createFeature(placedElements, isFinished, figures)
            placedElements.forEach { processedElements[it] = feature }
        }
    }

    return processedElements.values.toSet()
}

/**
 * Generic helper for features that are built by following connected split-edge elements (Field).
 */
fun <E : Element<ElementPosition.SplitEdge>, F : Feature> Board.getSplitEdgeFeatures(
    key: ElementKey<E>,
    createFeature: (placedElements: Set<PlacedElement<E>>, figures: List<PlayerFigure>) -> F,
): Set<F> {
    val processedElements: MutableMap<PlacedElement<E>, F> = mutableMapOf()

    tiles.forEach { (coordinates, tile) ->
        val tileElements = tile.elements[key]

        tileElements.forEach { element: E ->
            val placedElement = PlacedElement(coordinates = coordinates, element = element)

            if (placedElement in processedElements)
                return@forEach

            val placedElements = mutableSetOf<PlacedElement<E>>()

            fun follow(current: PlacedElement<E>) {
                if (current in placedElements)
                    return
                placedElements += current

                val neighbors = current.neighborElements(tiles = tiles, key = key)
                neighbors.filterNotNull().forEach(::follow)
            }

            follow(placedElement)

            val figures = getFiguresFromFeature(listOf(placedElement))

            val feature = createFeature(placedElements, figures)
            placedElements.forEach { processedElements[it] = feature }
        }
    }

    return processedElements.values.toSet()
}

/**
 * Generic helper for features based on surrounding tiles (Monastery, Garden).
 */
fun <E : Element<ElementPosition.Center>, F : Feature> Board.getCenterFeatures(
    key: ElementKey<E>,
    createFeature: (placedElement: PlacedElement<E>, neighborCount: Int, figures: List<PlayerFigure>) -> F,
): Set<F> {
    val processedElements: MutableMap<PlacedElement<E>, F> = mutableMapOf()

    tiles.forEach { (coordinates, tile) ->
        val tileElements = tile.elements[key]

        // There can be at most a single monastery or garden on a tile
        tileElements.forEach { element: E ->
            val placedElement = PlacedElement(coordinates = coordinates, element = element)

            val surroundingCoordinates = coordinates.surroundingCoordinates()
            val surroundingTiles = surroundingCoordinates.mapNotNull { tiles[it] }

            val figures = getFiguresFromFeature(listOf(placedElement))

            val feature = createFeature(placedElement, surroundingTiles.count(), figures)
            processedElements[placedElement] = feature
        }
    }

    return processedElements.values.toSet()
}
