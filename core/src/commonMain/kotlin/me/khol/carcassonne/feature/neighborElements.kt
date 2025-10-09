package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.BoardTiles
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementKey
import me.khol.carcassonne.ElementPosition
import kotlin.jvm.JvmName

/**
 * A null value in the returned collection means the feature is not finished yet.
 */
@JvmName("neighborEdgeElement")
fun <E : Element<ElementPosition.Edge>> PlacedElement<E>.neighborElements(
    board: Board,
    key: ElementKey<E>,
): Collection<PlacedElement<E>?> = neighborElements(
    tiles = board.tiles,
    key = key,
    oppositeCoordinates = { edge ->
        when (edge) {
            ElementPosition.Edge.Top -> top
            ElementPosition.Edge.Right -> right
            ElementPosition.Edge.Bottom -> bottom
            ElementPosition.Edge.Left -> left
        }
    },
    oppositeEdge = {
        when (this) {
            ElementPosition.Edge.Top -> ElementPosition.Edge.Bottom
            ElementPosition.Edge.Right -> ElementPosition.Edge.Left
            ElementPosition.Edge.Bottom -> ElementPosition.Edge.Top
            ElementPosition.Edge.Left -> ElementPosition.Edge.Right
        }
    },
)

/**
 * A null value in the returned collection means the feature is not finished yet.
 */
@JvmName("neighborSplitEdgeElement")
fun <E : Element<ElementPosition.SplitEdge>> PlacedElement<E>.neighborElements(
    board: Board,
    key: ElementKey<E>,
): Collection<PlacedElement<E>?> = neighborElements(
    tiles = board.tiles,
    key = key,
    oppositeCoordinates = { edge ->
        when (edge) {
            ElementPosition.SplitEdge.TopLeft -> top
            ElementPosition.SplitEdge.TopRight -> top
            ElementPosition.SplitEdge.RightTop -> right
            ElementPosition.SplitEdge.RightBottom -> right
            ElementPosition.SplitEdge.BottomLeft -> bottom
            ElementPosition.SplitEdge.BottomRight -> bottom
            ElementPosition.SplitEdge.LeftTop -> left
            ElementPosition.SplitEdge.LeftBottom -> left
        }
    },
    oppositeEdge = {
        when (this) {
            ElementPosition.SplitEdge.TopLeft -> ElementPosition.SplitEdge.BottomLeft
            ElementPosition.SplitEdge.TopRight -> ElementPosition.SplitEdge.BottomRight
            ElementPosition.SplitEdge.RightTop -> ElementPosition.SplitEdge.LeftTop
            ElementPosition.SplitEdge.RightBottom -> ElementPosition.SplitEdge.LeftBottom
            ElementPosition.SplitEdge.BottomLeft -> ElementPosition.SplitEdge.TopLeft
            ElementPosition.SplitEdge.BottomRight -> ElementPosition.SplitEdge.TopRight
            ElementPosition.SplitEdge.LeftTop -> ElementPosition.SplitEdge.RightTop
            ElementPosition.SplitEdge.LeftBottom -> ElementPosition.SplitEdge.RightBottom
        }
    },
)

private fun <P : ElementPosition, E : Element<P>> PlacedElement<E>.neighborElements(
    tiles: BoardTiles,
    key: ElementKey<E>,
    oppositeCoordinates: Coordinates.(P) -> Coordinates,
    oppositeEdge: P.() -> P,
): Set<PlacedElement<E>?> =
    element.positions.mapTo(mutableSetOf()) { elementPosition ->
        val oppositeCoordinates = coordinates.oppositeCoordinates(elementPosition)
        val otherTile = tiles[oppositeCoordinates]
        if (otherTile == null) {
            // empty space on board
            null
        } else {
            val oppositeElementPosition = elementPosition.oppositeEdge()
            // it is guaranteed to have the same element type on the other tile
            PlacedElement(
                coordinates = oppositeCoordinates,
                element = otherTile.elements[key].first { oppositeElementPosition in it.positions }
            )
        }
    }
