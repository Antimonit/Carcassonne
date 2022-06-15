package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F12 = Tile(
    name = "BB6F12",
    edges = Edges(top = Field, right = Field, bottom = River, left = Field),
    elements = elements {
        add(
            Element.Field,
            ElementGroup.field { all },
        )
        add(
            Element.River,
            ElementGroup.edges { bottom },
        )
        add(
            Element.RiverEnd,
            ElementGroup.Center,
        )
    },
)
