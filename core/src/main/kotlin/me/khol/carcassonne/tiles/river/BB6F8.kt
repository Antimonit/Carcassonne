package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F8 = Tile(
    name = "BB6F8",
    edges = Edges(top = Field, right = River, bottom = Road, left = River),
    elements = elements {
        add(
            Element.Field,
            Positions.field { leftTop + top + rightTop },
            Positions.field { leftBottom + bottomLeft },
            Positions.field { rightBottom + bottomRight },
        )
        add(
            Element.River,
            Positions.edges { left + right },
        )
        add(
            Element.Road,
            Positions.road { bottom },
        )
        add(
            Element.Monastery,
            Positions.Center,
        )
    },
)
