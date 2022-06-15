package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val I = Tile(
    name = "I",
    edges = Edges(top = City, right = Field, bottom = Field, left = City),
    elements = elements {
        val cityLeft = ElementGroup.city { left }
        val cityTop = ElementGroup.city { top }
        add(
            Element.Field,
            ElementGroup.field(cityLeft, cityTop) { right + bottom },
        )
        add(
            Element.City,
            cityLeft,
            cityTop,
        )
    },
)
