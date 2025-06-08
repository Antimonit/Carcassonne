package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val EC = Tile(
    name = "EC",
    edges = Edges(top = Field, right = Road, bottom = Road, left = Road),
    elements = elements {
        road(Boon.Road.Inn) { right }
        road { bottom }
        road(Boon.Road.Inn) { left }
        field { leftTop + top + rightTop }
        field { rightBottom + bottomRight }
        field { leftBottom + bottomLeft }
    },
)
