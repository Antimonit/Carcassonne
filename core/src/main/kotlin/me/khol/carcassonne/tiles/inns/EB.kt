package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EB = Tile(
    name = "EB",
    edges = Edges(top = Field, right = Road, bottom = Field, left = Road),
    elements = elements {
        add(
            Element.Road,
            ElementGroup.road(Boon.Road.Inn) { right + left },
        )
        add(
            Element.Field,
            ElementGroup.field { leftTop + top + rightTop },
            ElementGroup.field { leftBottom + bottom + rightBottom },
        )
        add(
            Element.Garden,
            ElementGroup.Center,
        )
    },
)
