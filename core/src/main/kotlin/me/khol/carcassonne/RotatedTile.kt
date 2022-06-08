package me.khol.carcassonne

data class RotatedTile(
    val tile: Tile,
    val rotation: Rotation,
) {
    val rotatedEdges = with(tile.edges) {
        when (rotation) {
            Rotation.ROTATE_0 ->
                Tile.Edges(top = top, right = right, bottom = bottom, left = left)
            Rotation.ROTATE_90 ->
                Tile.Edges(top = left, right = top, bottom = right, left = bottom)
            Rotation.ROTATE_180 ->
                Tile.Edges(top = bottom, right = left, bottom = top, left = right)
            Rotation.ROTATE_270 ->
                Tile.Edges(top = right, right = bottom, bottom = left, left = top)
        }
    }
}
