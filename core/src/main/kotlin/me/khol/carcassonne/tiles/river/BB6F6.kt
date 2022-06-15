package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F6 = Tile(
    name = "BB6F6",
    edges = Edges(top = River, right = Field, bottom = River, left = Field),
    elements = elements {
        add(
            Element.Field,
            ElementGroup.field { topLeft + left + bottomLeft },
            ElementGroup.field { topRight + right + bottomRight },
        )
        add(
            Element.River,
            ElementGroup.edges { top + bottom },
        )
    },
)
