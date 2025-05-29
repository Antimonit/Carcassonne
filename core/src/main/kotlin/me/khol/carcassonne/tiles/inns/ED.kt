package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.monastery
import me.khol.carcassonne.road

val ED = Tile(
    name = "ED",
    edges = Edges(top = Field, right = Road, bottom = Field, left = Road),
    elements = elements {
        road { right }
        road { left }
        field { leftTop + top + rightTop }
        field { leftBottom + bottom + rightBottom }
        monastery()
    },
)
