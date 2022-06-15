package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
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
            Positions.road { top },
            Positions.road { right },
            Positions.road { bottom },
            Positions.road { left },
        )
        add(
            Element.Field,
            Positions.field { leftTop + topLeft },
            Positions.field { topRight + rightTop },
            Positions.field { rightBottom + bottomRight },
            Positions.field { leftBottom + bottomLeft },
        )
    },
)
