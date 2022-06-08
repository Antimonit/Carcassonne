package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
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
            Positions.road(Boon.Road.Inn) { right + left },
        )
        add(
            Element.Field,
            Positions.field { leftTop + top + rightTop },
            Positions.field { leftBottom + bottom + rightBottom },
        )
        add(
            Element.Garden,
            Positions.Center,
        )
    },
)
