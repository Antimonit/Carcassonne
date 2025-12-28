package me.khol.carcassonne

data class RotatedTile(
    val tile: Tile,
    val rotation: Rotation,
) {

    val rotatedElements: RotatedElements
        get() = RotatedElements(
            elements = tile.elements,
            rotation = rotation,
        )
}

fun Tile.rotated(rotation: Rotation) =
    RotatedTile(tile = this, rotation = rotation)
