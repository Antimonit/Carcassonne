package me.khol.carcassonne

data class RotatedTile(
    val tile: Tile,
    val rotation: Rotation,
) {
    val rotatedTile: Tile by lazy {
        tile.copy(
            edges = tile.edges.rotate(rotation),
            elements = tile.elements.rotate(rotation),
        )
    }

    val rotatedEdges: Tile.Edges
        get() = rotatedTile.edges
}
