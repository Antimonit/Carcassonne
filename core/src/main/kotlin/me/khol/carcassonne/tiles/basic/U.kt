package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
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
            Positions.road { top + bottom },
        )
        add(
            Element.Field,
            Positions.field { topLeft + left + bottomLeft },
            Positions.field { topRight + right + bottomRight },
        )
    },
)
