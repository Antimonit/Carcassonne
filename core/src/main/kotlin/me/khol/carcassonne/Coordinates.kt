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
}
