package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val K = Tile(
    name = "K",
    edges = Edges(top = City, right = Field, bottom = Road, left = Road),
    elements = elements {
        add(
            Element.Field,
            Positions.splitEdges { Right + BottomRight + LeftTop },
            Positions.splitEdges { BottomLeft + LeftBottom },
        )
        add(
            Element.Road,
            Positions.edges { Bottom + Left },
        )
        add(
            Element.City,
            Positions.Edge.Top,
        )
    },
)
