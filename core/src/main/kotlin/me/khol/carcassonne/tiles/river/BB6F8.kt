package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F8 = Tile(
    name = "BB6F8",
    edges = Edges(top = Field, right = River, bottom = Road, left = River),
    elements = elements {
        add(
            Element.Field,
            ElementGroup.field { leftTop + top + rightTop },
            ElementGroup.field { leftBottom + bottomLeft },
            ElementGroup.field { rightBottom + bottomRight },
        )
        add(
            Element.River,
            ElementGroup.edges { left + right },
        )
        add(
            Element.Road,
            ElementGroup.road { bottom },
        )
        add(
            Element.Monastery,
            ElementGroup.Center,
        )
    },
)
