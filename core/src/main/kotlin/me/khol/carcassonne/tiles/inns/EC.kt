package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
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
            Positions.road(Boon.Road.Inn) { right },
            Positions.road { bottom },
            Positions.road(Boon.Road.Inn) { left },
        )
        add(
            Element.Field,
            Positions.field { leftTop + top + rightTop },
            Positions.field { rightBottom + bottomRight },
            Positions.field { leftBottom + bottomLeft },
        )
    },
)
