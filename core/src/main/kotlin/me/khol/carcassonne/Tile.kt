package me.khol.carcassonne

data class Tile(
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
