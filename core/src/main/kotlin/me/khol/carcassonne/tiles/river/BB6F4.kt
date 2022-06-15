package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F4 = Tile(
    name = "BB6F4",
    edges = Edges(top = Field, right = River, bottom = Field, left = River),
    elements = elements {
        add(
            Element.Field,
            ElementGroup.field { leftTop + top + rightTop },
            ElementGroup.field { rightBottom + bottom + leftBottom },
        )
        add(
            Element.River,
            ElementGroup.edges { left + right },
        )
    },
)
