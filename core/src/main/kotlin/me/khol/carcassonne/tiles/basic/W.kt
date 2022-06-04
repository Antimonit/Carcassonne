package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val W = Tile(
    name = "W",
    edges = Edges(top = Field, right = Road, bottom = Road, left = Road),
    elements = elements {
        add(
            Element.Road,
            Positions.Edge.Right,
            Positions.Edge.Bottom,
            Positions.Edge.Left
        )
        add(
            Element.Field,
            Positions.splitEdges { LeftTop + Top + RightTop },
            Positions.splitEdges { RightBottom + BottomRight },
            Positions.splitEdges { LeftBottom + BottomLeft },
        )
    },
)
