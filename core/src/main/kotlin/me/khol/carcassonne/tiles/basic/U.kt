package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val U = Tile(
    name = "U",
    edges = Edges(top = Road, right = Field, bottom = Road, left = Field),
    elements = elements {
        add(
            Element.Road,
            Positions.edges { Top + Bottom },
        )
        add(
            Element.Field,
            Positions.splitEdges { TopLeft + Left + BottomLeft },
            Positions.splitEdges { TopRight + Right + BottomRight },
        )
    },
)
