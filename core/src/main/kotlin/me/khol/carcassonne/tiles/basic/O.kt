package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val O = Tile(
    name = "O",
    edges = Edges(top = City, right = Road, bottom = Road, left = City),
    elements = elements {
        add(
            Element.Field,
            Positions.splitEdges { RightTop + BottomLeft },
            Positions.splitEdges { RightBottom + BottomRight },
        )
        add(
            Element.Road,
            Positions.edges { Right + Bottom }
        )
        add(
            Element.City,
            Positions.edges { Top + Left },
        )
    },
)
// TODO: Add Coat of Arms