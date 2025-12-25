package me.khol.carcassonne

data class RotatedTile(
    val tile: Tile,
    val rotation: Rotation,
) {
    private val rotatedTile: Tile by lazy { asTile() }

    val edges: Tile.Edges
        get() = rotatedTile.edges

    val elements: Elements
        get() = rotatedTile.elements
}

fun RotatedTile.asTile(): Tile =
    tile.rotate(rotation)

fun Tile.rotated(rotation: Rotation) =
    RotatedTile(tile = this, rotation = rotation)
