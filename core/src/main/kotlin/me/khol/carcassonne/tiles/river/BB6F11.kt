package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F11 = Tile(
    name = "BB6F11",
    edges = Edges(top = River, right = Road, bottom = River, left = Road),
    elements = elements {
        add(
            Element.Field,
            Positions.field { leftTop + topLeft },
            Positions.field { topRight + rightTop },
            Positions.field { rightBottom + bottomRight },
            Positions.field { leftBottom + bottomLeft },
        )
        add(
            Element.River,
            Positions.edges { top + bottom },
        )
        add(
            Element.Road,
            Positions.road { left + right },
        )
    },
)

