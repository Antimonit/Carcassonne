package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
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
            Positions.splitEdges { TopLeft + LeftTop },
            Positions.splitEdges { TopRight + RightTop + BottomLeft + LeftBottom },
            Positions.splitEdges { BottomRight + RightBottom },
        )
        add(
            Element.Road,
            Positions.edges { Top + Left },
        )
        add(
            Element.River,
            Positions.edges { Right + Bottom },
        )
    },
)

