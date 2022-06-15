package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EE = Tile(
    name = "EE",
    edges = Edges(top = Road, right = Road, bottom = Road, left = Road),
    elements = elements {
        add(
            Element.Road,
            ElementGroup.road { left + top },
            ElementGroup.road { right + bottom },
        )
        add(
            Element.Field,
            ElementGroup.field { leftTop + topLeft },
            ElementGroup.field { topRight + rightTop + bottomLeft + leftBottom },
            ElementGroup.field { rightBottom + bottomRight },
        )
    },
)
