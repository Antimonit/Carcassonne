package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val EE = Tile(
    name = "EE",
    edges = Edges(top = Road, right = Road, bottom = Road, left = Road),
    elements = elements {
        road { left + top }
        road { right + bottom }
        field { leftTop + topLeft }
        field { topRight + rightTop + bottomLeft + leftBottom }
        field { rightBottom + bottomRight }
    },
)
