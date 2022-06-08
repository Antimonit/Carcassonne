package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F3 = Tile(
    name = "BB6F3",
    edges = Edges(top = City, right = River, bottom = City, left = River),
    elements = elements {
        add(
            Element.Field,
            Positions.splitEdges { LeftTop + RightTop },
            Positions.splitEdges { RightBottom + LeftBottom },
        )
        add(
            Element.River,
            Positions.edges { Left + Right },
        )
        add(
            Element.City,
            Positions.Edge.Top,
            Positions.Edge.Bottom,
        )
    },
)

