package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val J = Tile(
    name = "J",
    edges = Edges(top = City, right = Road, bottom = Road, left = Field),
    elements = elements {
        add(
            Element.Field,
            Positions.splitEdges { Left + BottomLeft + RightTop },
            Positions.splitEdges { BottomRight + RightBottom },
        )
        add(
            Element.Road,
            Positions.edges { Bottom + Right },
        )
        add(
            Element.City,
            Positions.Edge.Top,
        )
    },
)
