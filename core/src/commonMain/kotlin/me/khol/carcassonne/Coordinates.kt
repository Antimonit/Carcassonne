package me.khol.carcassonne

@JvmInline
value class Coordinates private constructor(
    private val packedValue: Long,
) {

    constructor(x: Int, y: Int) : this((x.toLong() shl 32) or (y.toLong() and 0xFFFFFFFF))

    val x: Int
        get() = (packedValue shr 32).toInt()
    val y: Int
        get() = (packedValue and 0xFFFFFFFF).toInt()

    override fun toString(): String {
        return "[$x, $y]"
    }

    fun copy(x: Int = this.x, y: Int = this.y) = Coordinates(x = x, y = y)

    val top: Coordinates inline get() = copy(y = y - 1)
    val bottom: Coordinates inline get() = copy(y = y + 1)
    val left: Coordinates inline get() = copy(x = x - 1)
    val right: Coordinates inline get() = copy(x = x + 1)
    val neighbors: List<Coordinates> get() = listOf(top, bottom, left, right)
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
