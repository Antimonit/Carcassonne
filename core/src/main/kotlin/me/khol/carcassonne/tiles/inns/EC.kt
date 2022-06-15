package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EC = Tile(
    name = "EC",
    edges = Edges(top = Field, right = Road, bottom = Road, left = Road),
    elements = elements {
        add(
            Element.Road,
            ElementGroup.road(Boon.Road.Inn) { right },
            ElementGroup.road { bottom },
            ElementGroup.road(Boon.Road.Inn) { left },
        )
        add(
            Element.Field,
            ElementGroup.field { leftTop + top + rightTop },
            ElementGroup.field { rightBottom + bottomRight },
            ElementGroup.field { leftBottom + bottomLeft },
        )
    },
)
