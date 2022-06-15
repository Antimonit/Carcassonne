package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val X = Tile(
    name = "X",
    edges = Edges(top = Road, right = Road, bottom = Road, left = Road),
    elements = elements {
        add(
            Element.Road,
            ElementGroup.road { top },
            ElementGroup.road { right },
            ElementGroup.road { bottom },
            ElementGroup.road { left },
        )
        add(
            Element.Field,
            ElementGroup.field { leftTop + topLeft },
            ElementGroup.field { topRight + rightTop },
            ElementGroup.field { rightBottom + bottomRight },
            ElementGroup.field { leftBottom + bottomLeft },
        )
    },
)
