package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F9 = Tile(
    name = "BB6F9",
    edges = Edges(top = Road, right = River, bottom = River, left = Road),
    elements = elements {
        add(
            Element.Field,
            ElementGroup.field { topLeft + leftTop },
            ElementGroup.field { topRight + rightTop + bottomLeft + leftBottom },
            ElementGroup.field { bottomRight + rightBottom },
        )
        add(
            Element.Road,
            ElementGroup.road { top + left },
        )
        add(
            Element.River,
            ElementGroup.edges { right + bottom },
        )
    },
)

