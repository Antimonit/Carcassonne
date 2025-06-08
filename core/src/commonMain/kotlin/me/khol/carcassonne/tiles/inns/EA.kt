package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val EA = Tile(
    name = "EA",
    edges = Edges(top = Field, right = Field, bottom = Road, left = Road),
    elements = elements {
        road(Boon.Road.Inn) { bottom + left }
        field { leftBottom + bottomLeft }
        field { leftTop + top + right + bottomRight }
    },
)
