package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.cropCircle
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val Korn2 = Tile(
    name = "Korn2",
    edges = Edges(top = Road, right = Road, bottom = Road, left = Road),
    elements = elements {
        field { leftBottom + bottomLeft }
        field { leftTop + topLeft + rightBottom + bottomRight }
        field { topRight + rightTop }
        road { top }
        road { right }
        road { left + bottom }
        cropCircle()
    },
)
