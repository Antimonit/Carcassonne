package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val N = Tile(
    name = "N",
    edges = Edges(top = City, right = City, bottom = Field, left = Field),
    elements = elements {
        add(
            Element.Field,
            Positions.splitEdges { Left + Bottom },
        )
        add(
            Element.City,
            Positions.edges { Top + Right },
        )
    },
)
