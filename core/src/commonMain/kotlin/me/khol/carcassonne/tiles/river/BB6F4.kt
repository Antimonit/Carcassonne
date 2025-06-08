package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.river

val BB6F4 = Tile(
    name = "BB6F4",
    edges = Edges(top = Field, right = River, bottom = Field, left = River),
    elements = elements {
        field { leftTop + top + rightTop }
        field { rightBottom + bottom + leftBottom }
        river { left + right }
    },
)
