package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val A = Tile(
    name = "A",
    edges = Edges(top = Field, right = Field, bottom = Road, left = Field),
    elements = elements {
        add(
            Element.Field,
            Positions.SplitEdge.All,
        )
        add(
            Element.Road,
            Positions.Edge.Bottom,
        )
        add(
            Element.Monastery,
            Positions.Center,
        )
    },
)
