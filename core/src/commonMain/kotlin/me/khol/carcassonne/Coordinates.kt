package me.khol.carcassonne

data class Coordinates(
    val x: Int,
    val y: Int,
) {
    val top: Coordinates by lazy { copy(y = y - 1) }
    val bottom: Coordinates by lazy { copy(y = y + 1) }
    val left: Coordinates by lazy { copy(x = x - 1) }
    val right: Coordinates by lazy { copy(x = x + 1) }
    // TODO: Rename to orthogonal neighbors
    val neighbors: List<Coordinates> by lazy { listOf(top, bottom, left, right) }

    override fun toString(): String {
        return "[$x, $y]"
    }
}

/**
 * Used by Monastery and Garden features.
 */
fun Coordinates.surroundingCoordinates(): Set<Coordinates> {
    return (-1..1).flatMapTo(mutableSetOf()) { xOffset ->
        (-1..1).map { yOffset ->
            copy(x = x + xOffset, y = y + yOffset)
        }
    }
}
