package me.khol.carcassonne

data class Coordinates(
    val x: Int,
    val y: Int,
) {
    val top: Coordinates by lazy { copy(y = y + 1) }
    val bottom: Coordinates by lazy { copy(y = y - 1) }
    val left: Coordinates by lazy { copy(x = x - 1) }
    val right: Coordinates by lazy { copy(x = x + 1) }
    val neighbors: List<Coordinates> by lazy { listOf(top, bottom, left, right) }

    override fun toString(): String {
        return "[$x, $y]"
    }
}

/**
 * Used by Monastery and Garden features.
 */
fun Coordinates.surroundingCoordinates(): List<Coordinates> {
    return (-1..1).map { xOffset ->
        (-1..1).map { yOffset ->
            copy(x = x + xOffset, y = y + yOffset)
        }
    }.flatten()
}

fun Coordinates.oppositeCoordinates(edge: ElementPosition.Edge): Coordinates =
    when (edge) {
        ElementPosition.Edge.Top -> top
        ElementPosition.Edge.Right -> right
        ElementPosition.Edge.Bottom -> bottom
        ElementPosition.Edge.Left -> left
    }

fun Coordinates.oppositeCoordinates(edge: ElementPosition.SplitEdge): Coordinates =
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
