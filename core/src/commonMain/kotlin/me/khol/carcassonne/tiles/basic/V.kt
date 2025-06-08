package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.garden
import me.khol.carcassonne.road

val V = Tile(
    name = "V",
    edges = Edges(top = Field, right = Field, bottom = Road, left = Road),
    elements = elements {
        road { bottom + left }
        field { leftBottom + bottomLeft }
        field { leftTop + top + right + bottomRight }
    },
)

val V_G = Tile(
    name = "V_G",
    edges = Edges(top = Field, right = Field, bottom = Road, left = Road),
    elements = elements {
        garden()
        road { bottom + left }
        field { leftBottom + bottomLeft }
        field { leftTop + top + right + bottomRight }
    },
)
