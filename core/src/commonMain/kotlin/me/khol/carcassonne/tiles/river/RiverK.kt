package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.river
import me.khol.carcassonne.road

val RiverK = Tile(
    name = "RiverK",
    edges = Edges(top = River, right = Road, bottom = River, left = Road),
    elements = elements {
        field { leftTop + topLeft }
        field { topRight + rightTop }
        field { rightBottom + bottomRight }
        field { leftBottom + bottomLeft }
        river { top + bottom }
        road { left + right }
    },
)

