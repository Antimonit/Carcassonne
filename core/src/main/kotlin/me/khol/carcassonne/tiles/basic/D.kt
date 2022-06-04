package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val D = Tile(
    name = "D",
    edges = Edges(top = City, right = Road, bottom = Field, left = Road),
    elements = elements {
        add(
            Element.Field,
            Positions.splitEdges { LeftTop + RightTop },
            Positions.splitEdges { LeftBottom + RightBottom + Bottom },
        )
        add(
            Element.City,
            Positions.Edge.Top,
        )
        add(
            Element.Road,
            Positions.edges { Left + Right },
        )
    },
)
