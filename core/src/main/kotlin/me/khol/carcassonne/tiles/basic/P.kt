package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val P = Tile(
    name = "P",
    edges = Edges(top = City, right = Road, bottom = Road, left = City),
    elements = elements {
        val city = Positions.city { top + left }
        add(
            Element.Field,
            Positions.field(city) { rightTop + bottomLeft },
            Positions.field { rightBottom + bottomRight },
        )
        add(
            Element.Road,
            Positions.road { right + bottom },
        )
        add(
            Element.City,
            city,
        )
    },
)
