package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val W = Tile(
    name = "W",
    edges = Edges(top = Field, right = Road, bottom = Road, left = Road),
    elements = elements {
        road { right }
        road { bottom }
        road { left }
        field { leftTop + top + rightTop }
        field { rightBottom + bottomRight }
        field { leftBottom + bottomLeft }
    },
)
