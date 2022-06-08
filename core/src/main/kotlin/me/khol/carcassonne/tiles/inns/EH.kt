package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EH = Tile(
    name = "EH",
    edges = Edges(top = City, right = City, bottom = City, left = City),
    elements = elements {
        val cityTop = Positions.city { top }
        val cityRight = Positions.city { right }
        val cityBottom = Positions.city { bottom }
        val cityLeft = Positions.city { left }
        add(
            Element.City,
            cityTop,
            cityRight,
            cityBottom,
            cityLeft,
        )
        add(
            Element.Field,
            Positions.field(cityTop, cityRight, cityBottom, cityLeft) { none },
        )
    },
)
