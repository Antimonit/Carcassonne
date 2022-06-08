package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F1 = Tile(
    name = "BB6F1",
    edges = Edges(top = Field, right = Field, bottom = River, left = Field),
    elements = elements {
        add(
            Element.Field,
            Positions.SplitEdge.All,
        )
        add(
            Element.River,
            Positions.Edge.Bottom,
        )
        add(
            Element.RiverStart,
            Positions.Center,
        )
    },
)
