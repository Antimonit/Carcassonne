package me.khol.carcassonne.tiles

import me.khol.carcassonne.Tile

class Tileset(
    val tileCounts: List<TileCount>,
)

class TileCount(
    val tile: Tile,
    val count: Int,
)
