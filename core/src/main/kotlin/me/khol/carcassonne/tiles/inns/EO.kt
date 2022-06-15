package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EO = Tile(
    name = "EO",
    edges = Edges(top = City, right = City, bottom = Field, left = City),
    elements = elements {
        val cityTop = ElementGroup.city { top }
        val cityRight = ElementGroup.city { right }
        val cityLeft = ElementGroup.city { left }
        add(
            Element.City,
            cityTop,
            cityRight,
            cityLeft,
        )
        add(
            Element.Field,
            ElementGroup.field(cityTop, cityRight, cityLeft) { bottom },
        )
    },
)
