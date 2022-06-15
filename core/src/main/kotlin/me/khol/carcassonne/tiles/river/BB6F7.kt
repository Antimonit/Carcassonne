package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F7 = Tile(
    name = "BB6F7",
    edges = Edges(top = River, right = Field, bottom = Field, left = River),
    elements = elements {
        add(
            Element.Field,
            Positions.field { topLeft + leftTop },
            Positions.field { topRight + right + bottom + leftBottom },
        )
        add(
            Element.River,
            Positions.edges { top + left },
        )
    },
)
