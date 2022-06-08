package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F6 = Tile(
    name = "BB6F6",
    edges = Edges(top = River, right = Field, bottom = River, left = Field),
    elements = elements {
        add(
            Element.Field,
            Positions.splitEdges { TopLeft + Left + BottomLeft },
            Positions.splitEdges { TopRight + Right + BottomRight },
        )
        add(
            Element.River,
            Positions.edges { Top + Bottom },
        )
    },
)
