package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val P = Tile(
    name = "P",
    edges = Edges(top = City, right = Road, bottom = Road, left = City),
    elements = elements {
        val city = ElementGroup.city { top + left }
        add(
            Element.Field,
            ElementGroup.field(city) { rightTop + bottomLeft },
            ElementGroup.field { rightBottom + bottomRight },
        )
        add(
            Element.Road,
            ElementGroup.road { right + bottom },
        )
        add(
            Element.City,
            city,
        )
    },
)
