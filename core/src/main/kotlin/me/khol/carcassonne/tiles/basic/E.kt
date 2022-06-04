package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val E = Tile(
    name = "E",
    edges = Edges(top = City, right = Field, bottom = Field, left = Field),
    elements = elements {
        add(
            Element.Field,
            Positions.splitEdges { Left + Right + Bottom },
        )
        add(
            Element.City,
            Positions.Edge.Top,
        )
    },
)
