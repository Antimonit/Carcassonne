package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val L = Tile(
    name = "L",
    edges = Edges(top = City, right = Road, bottom = Road, left = Road),
    elements = elements {
        add(
            Element.Field,
            Positions.splitEdges { RightTop + LeftTop },
            Positions.splitEdges { BottomLeft + LeftBottom },
            Positions.splitEdges { BottomRight + RightBottom },
        )
        add(
            Element.Road,
            Positions.Edge.Left,
            Positions.Edge.Bottom,
            Positions.Edge.Right,
        )
        add(
            Element.City,
            Positions.Edge.Top,
        )
    },
)
