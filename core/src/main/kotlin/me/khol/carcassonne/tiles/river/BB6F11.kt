package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F11 = Tile(
    name = "BB6F11",
    edges = Edges(top = River, right = Road, bottom = River, left = Road),
    elements = elements {
        add(
            Element.Field,
            ElementGroup.field { leftTop + topLeft },
            ElementGroup.field { topRight + rightTop },
            ElementGroup.field { rightBottom + bottomRight },
            ElementGroup.field { leftBottom + bottomLeft },
        )
        add(
            Element.River,
            ElementGroup.edges { top + bottom },
        )
        add(
            Element.Road,
            ElementGroup.road { left + right },
        )
    },
)

