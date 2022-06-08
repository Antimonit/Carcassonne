package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F2 = Tile(
    name = "BB6F2",
    edges = Edges(top = City, right = River, bottom = Road, left = River),
    elements = elements {
        add(
            Element.Field,
            Positions.SplitEdge.LeftTop,
            Positions.SplitEdge.RightTop,
            Positions.splitEdges { RightBottom + BottomRight },
            Positions.splitEdges { BottomLeft + LeftBottom },
        )
        add(
            Element.River,
            Positions.edges { Left + Right },
        )
        add(
            Element.Road,
            Positions.Edge.Bottom,
        )
        add(
            Element.City,
            Positions.Edge.Top,
        )
    },
)
