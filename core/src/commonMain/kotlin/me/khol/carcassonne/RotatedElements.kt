package me.khol.carcassonne

data class RotatedElements(
    val elements: Elements,
    val rotation: Rotation,
) {

    fun all(): List<RotatedElement<*>> =
        elements.all().map { it.rotated(rotation) }
}
