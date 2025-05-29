package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.river
import me.khol.carcassonne.road

val BB6F9 = Tile(
    name = "BB6F9",
    edges = Edges(top = Road, right = River, bottom = River, left = Road),
    elements = elements {
        field { topLeft + leftTop }
        field { topRight + rightTop + bottomLeft + leftBottom }
        field { bottomRight + rightBottom }
        road { top + left }
        river { right + bottom }
    },
)

