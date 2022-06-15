package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F5 = Tile(
    name = "BB6F5",
    edges = Edges(top = City, right = City, bottom = River, left = River),
    elements = elements {
        val city = Positions.city { top + right }
        add(
            Element.Field,
            Positions.field(city) { leftTop + bottomRight },
            Positions.field { leftBottom + bottomLeft },
        )
        add(
            Element.City,
            city,
        )
        add(
            Element.River,
            Positions.edges { left + bottom },
        )
    },
)
