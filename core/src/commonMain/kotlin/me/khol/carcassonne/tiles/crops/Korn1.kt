package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.cropCircle
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val Korn1 = Tile(
    name = "Korn1",
    edges = Edges(top = Field, right = Road, bottom = Road, left = Road),
    elements = elements {
        field { leftTop + top + rightTop }
        field { leftBottom + bottomLeft }
        field { rightBottom + bottomRight }
        road { right }
        road { left + bottom }
        cropCircle()
    },
)
