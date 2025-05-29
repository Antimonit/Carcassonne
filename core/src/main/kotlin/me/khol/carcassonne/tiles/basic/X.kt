package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val X = Tile(
    name = "X",
    edges = Edges(top = Road, right = Road, bottom = Road, left = Road),
    elements = elements {
        road { top }
        road { right }
        road { bottom }
        road { left }
        field { leftTop + topLeft }
        field { topRight + rightTop }
        field { rightBottom + bottomRight }
        field { leftBottom + bottomLeft }
    },
)
