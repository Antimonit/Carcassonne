package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.garden
import me.khol.carcassonne.river

val BB6F10 = Tile(
    name = "BB6F10",
    edges = Edges(top = Field, right = River, bottom = River, left = Field),
    elements = elements {
        field { rightTop + bottomLeft + left + top }
        field { bottomRight + rightBottom }
        river { right + bottom }
        garden()
    },
)
