package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val ED = Tile(
    name = "ED",
    edges = Edges(top = Field, right = Road, bottom = Field, left = Road),
    elements = elements {
        add(
            Element.Road,
            ElementGroup.road { right },
            ElementGroup.road { left },
        )
        add(
            Element.Field,
            ElementGroup.field { leftTop + top + rightTop },
            ElementGroup.field { leftBottom + bottom + rightBottom },
        )
        add(
            Element.Monastery,
            ElementGroup.Center,
        )
    },
)
