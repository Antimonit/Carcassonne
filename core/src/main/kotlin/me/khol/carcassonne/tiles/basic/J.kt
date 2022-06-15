package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val J = Tile(
    name = "J",
    edges = Edges(top = City, right = Road, bottom = Road, left = Field),
    elements = elements {
        val city = ElementGroup.city { top }
        add(
            Element.Field,
            ElementGroup.field(city) { left + bottomLeft + rightTop },
            ElementGroup.field { bottomRight + rightBottom },
        )
        add(
            Element.Road,
            ElementGroup.road { bottom + right },
        )
        add(
            Element.City,
            city,
        )
    },
)
