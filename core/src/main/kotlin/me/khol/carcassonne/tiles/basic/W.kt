package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val W = Tile(
    name = "W",
    edges = Edges(top = Field, right = Road, bottom = Road, left = Road),
    elements = elements {
        add(
            Element.Road,
            ElementGroup.road { right },
            ElementGroup.road { bottom },
            ElementGroup.road { left },
        )
        add(
            Element.Field,
            ElementGroup.field { leftTop + top + rightTop },
            ElementGroup.field { rightBottom + bottomRight },
            ElementGroup.field { leftBottom + bottomLeft },
        )
    },
)
