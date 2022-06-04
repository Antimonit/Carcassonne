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
            Positions.Edge.Top,
            Positions.Edge.Right,
            Positions.Edge.Bottom,
            Positions.Edge.Left
        )
        add(
            Element.Field,
            Positions.splitEdges { LeftTop + TopLeft },
            Positions.splitEdges { TopRight + RightTop },
            Positions.splitEdges { RightBottom + BottomRight },
            Positions.splitEdges { LeftBottom + BottomLeft },
        )
    },
)
