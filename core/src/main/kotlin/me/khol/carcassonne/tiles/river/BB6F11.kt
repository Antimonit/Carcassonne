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
            Positions.splitEdges { LeftTop + TopLeft },
            Positions.splitEdges { TopRight + RightTop },
            Positions.splitEdges { RightBottom + BottomRight },
            Positions.splitEdges { LeftBottom + BottomLeft },
        )
        add(
            Element.River,
            Positions.edges { Top + Bottom },
        )
        add(
            Element.Road,
            Positions.edges { Left + Right },
        )
    },
)

