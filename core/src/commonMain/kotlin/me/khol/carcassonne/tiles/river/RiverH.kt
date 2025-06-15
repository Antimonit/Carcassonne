package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.monastery
import me.khol.carcassonne.river
import me.khol.carcassonne.road

val RiverH = Tile(
    name = "RiverH",
    edges = Edges(top = Field, right = River, bottom = Road, left = River),
    elements = elements {
        field { leftTop + top + rightTop }
        field { leftBottom + bottomLeft }
        field { rightBottom + bottomRight }
        river { left + right }
        road { bottom }
        monastery()
    },
)
