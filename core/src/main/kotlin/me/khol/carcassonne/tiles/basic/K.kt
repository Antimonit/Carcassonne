package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val K = Tile(
    name = "K",
    edges = Edges(top = City, right = Field, bottom = Road, left = Road),
    elements = elements {
        val city = ElementGroup.city { top }
        add(
            Element.Field,
            ElementGroup.field(city) { right + bottomRight + leftTop },
            ElementGroup.field { bottomLeft + leftBottom },
        )
        add(
            Element.Road,
            ElementGroup.road { bottom + left },
        )
        add(
            Element.City,
            city,
        )
    },
)
