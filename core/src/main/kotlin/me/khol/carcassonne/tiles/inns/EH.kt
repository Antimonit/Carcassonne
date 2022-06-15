package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EH = Tile(
    name = "EH",
    edges = Edges(top = City, right = City, bottom = City, left = City),
    elements = elements {
        val cityTop = ElementGroup.city { top }
        val cityRight = ElementGroup.city { right }
        val cityBottom = ElementGroup.city { bottom }
        val cityLeft = ElementGroup.city { left }
        add(
            Element.City,
            cityTop,
            cityRight,
            cityBottom,
            cityLeft,
        )
        add(
            Element.Field,
            ElementGroup.field(cityTop, cityRight, cityBottom, cityLeft) { none },
        )
    },
)
