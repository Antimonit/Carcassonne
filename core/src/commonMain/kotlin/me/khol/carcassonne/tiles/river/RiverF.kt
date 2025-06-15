package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.river

val RiverF = Tile(
    name = "RiverF",
    edges = Edges(top = River, right = Field, bottom = River, left = Field),
    elements = elements {
        field { topLeft + left + bottomLeft }
        field { topRight + right + bottomRight }
        river { top + bottom }
    },
)
