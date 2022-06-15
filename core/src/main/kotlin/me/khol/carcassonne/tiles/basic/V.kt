package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val V = Tile(
    name = "V",
    edges = Edges(top = Field, right = Field, bottom = Road, left = Road),
    elements = elements {
        add(
            Element.Road,
            Positions.road { bottom + left },
        )
        add(
            Element.Field,
            Positions.field { leftBottom + bottomLeft },
            Positions.field { leftTop + top + right + bottomRight },
        )
    },
)
