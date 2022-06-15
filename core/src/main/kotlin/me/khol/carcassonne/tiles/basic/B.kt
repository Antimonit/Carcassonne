package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val B = Tile(
    name = "B",
    edges = Edges(top = Field, right = Field, bottom = Field, left = Field),
    elements = elements {
        add(
            Element.Field,
            Positions.field { all },
        )
        add(
            Element.Monastery,
            Positions.Center,
        )
    },
)
