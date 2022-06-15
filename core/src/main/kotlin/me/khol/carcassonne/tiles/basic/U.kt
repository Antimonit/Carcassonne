package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val U = Tile(
    name = "U",
    edges = Edges(top = Road, right = Field, bottom = Road, left = Field),
    elements = elements {
        add(
            Element.Road,
            ElementGroup.road { top + bottom },
        )
        add(
            Element.Field,
            ElementGroup.field { topLeft + left + bottomLeft },
            ElementGroup.field { topRight + right + bottomRight },
        )
    },
)
