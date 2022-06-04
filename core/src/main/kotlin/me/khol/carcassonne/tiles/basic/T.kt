package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val T = Tile(
    name = "T",
    edges = Edges(top = City, right = City, bottom = Road, left = City),
    elements = elements {
        add(
            Element.Field,
            Positions.SplitEdge.BottomLeft,
            Positions.SplitEdge.BottomRight,
        )
        add(
            Element.Road,
            Positions.Edge.Bottom,
        )
        add(
            Element.City,
            Positions.edges { Top + Right + Left },
        )
    },
)
