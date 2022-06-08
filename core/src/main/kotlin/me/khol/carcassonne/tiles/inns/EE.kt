package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
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
            Positions.road { left + top },
            Positions.road { right + bottom },
        )
        add(
            Element.Field,
            Positions.field { leftTop + topLeft },
            Positions.field { topRight + rightTop + bottomLeft + leftBottom },
            Positions.field { rightBottom + bottomRight },
        )
    },
)
