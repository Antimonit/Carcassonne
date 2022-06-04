package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val G = Tile(
    name = "G",
    edges = Edges(top = Field, right = City, bottom = Field, left = City),
    elements = elements {
        add(
            Element.Field,
            Positions.SplitEdge.Top,
            Positions.SplitEdge.Bottom,
        )
        add(
            Element.City,
            Positions.edges { Left + Right },
        )
    },
)
