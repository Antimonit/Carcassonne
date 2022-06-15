package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F10 = Tile(
    name = "BB6F10",
    edges = Edges(top = Field, right = River, bottom = River, left = Field),
    elements = elements {
        add(
            Element.Field,
            Positions.field { rightTop + bottomLeft + left + top },
            Positions.field { bottomRight + rightBottom },
        )
        add(
            Element.River,
            Positions.edges { right + bottom },
        )
        add(
            Element.Garden,
            Positions.Center,
        )
    },
)
