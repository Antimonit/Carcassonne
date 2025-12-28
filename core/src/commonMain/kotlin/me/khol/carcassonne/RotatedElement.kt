package me.khol.carcassonne

data class RotatedElement<E : Element<ElementPosition>>(
    val element: E,
    val rotation: Rotation,
)

fun <E : Element<ElementPosition>> E.rotated(rotation: Rotation) =
    RotatedElement(element = this, rotation = rotation)
