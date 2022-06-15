package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val T = Tile(
    name = "T",
    edges = Edges(top = City, right = City, bottom = Road, left = City),
    elements = elements {
        val city = ElementGroup.city { top + right + left }
        add(
            Element.Field,
            ElementGroup.field(city) { bottomLeft },
            ElementGroup.field(city) { bottomRight },
        )
        add(
            Element.Road,
            ElementGroup.road { bottom },
        )
        add(
            Element.City,
            city,
        )
    },
)
