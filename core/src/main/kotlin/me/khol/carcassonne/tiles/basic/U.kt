package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.garden
import me.khol.carcassonne.road

val U = Tile(
    name = "U",
    edges = Edges(top = Road, right = Field, bottom = Road, left = Field),
    elements = elements {
        road { top + bottom }
        field { topLeft + left + bottomLeft }
        field { topRight + right + bottomRight }
    },
)

val U_G = Tile(
    name = "U_G",
    edges = Edges(top = Road, right = Field, bottom = Road, left = Field),
    elements = elements {
        garden()
        road { top + bottom }
        field { topLeft + left + bottomLeft }
        field { topRight + right + bottomRight }
    },
)
