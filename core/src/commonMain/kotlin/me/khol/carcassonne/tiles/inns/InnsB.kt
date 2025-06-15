package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.garden
import me.khol.carcassonne.road

val InnsB = Tile(
    name = "InnsB",
    edges = Edges(top = Field, right = Road, bottom = Field, left = Road),
    elements = elements {
        road(Boon.Road.Inn) { right + left }
        field { leftTop + top + rightTop }
        field { leftBottom + bottom + rightBottom }
        garden()
    },
)
