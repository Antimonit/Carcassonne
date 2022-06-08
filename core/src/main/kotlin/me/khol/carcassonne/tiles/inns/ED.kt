package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
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
            Positions.road { right },
            Positions.road { left },
        )
        add(
            Element.Field,
            Positions.field { leftTop + top + rightTop },
            Positions.field { leftBottom + bottom + rightBottom },
        )
        add(
            Element.Monastery,
            Positions.Center,
        )
    },
)
