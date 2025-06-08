package me.khol.carcassonne

data class Tile(
    val name: String,
    val edges: Edges,
    val elements: Elements,
) {

    enum class Edge {
        Field,
        Road,
        City,
        River,
    }

    data class Edges(
        val top: Edge,
        val right: Edge,
        val bottom: Edge,
        val left: Edge,
    )
}

fun Tile.Edges.rotate(rotation: Rotation): Tile.Edges =
    when (rotation) {
        Rotation.ROTATE_0 -> Tile.Edges(top = top, right = right, bottom = bottom, left = left)
        Rotation.ROTATE_90 -> Tile.Edges(top = left, right = top, bottom = right, left = bottom)
        Rotation.ROTATE_180 -> Tile.Edges(top = bottom, right = left, bottom = top, left = right)
        Rotation.ROTATE_270 -> Tile.Edges(top = right, right = bottom, bottom = left, left = top)
    }
