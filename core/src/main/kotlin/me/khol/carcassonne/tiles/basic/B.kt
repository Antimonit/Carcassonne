package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
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
            ElementGroup.field { all },
        )
        add(
            Element.Monastery,
            ElementGroup.Center,
        )
    },
)
