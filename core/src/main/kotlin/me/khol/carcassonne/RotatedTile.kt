package me.khol.carcassonne

data class RotatedTile(
    val tile: Tile,
    val rotation: Rotation,
) {
    private val rotatedTile: Tile by lazy { asTile() }

    val rotatedEdges: Tile.Edges
        get() = rotatedTile.edges
}

fun RotatedTile.asTile(): Tile =
    tile.copy(
        edges = tile.edges.rotate(rotation),
        elements = tile.elements.rotate(rotation),
    )
