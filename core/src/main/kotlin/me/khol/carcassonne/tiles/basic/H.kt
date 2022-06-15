package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val H = Tile(
    name = "H",
    edges = Edges(top = Field, right = City, bottom = Field, left = City),
    elements = elements {
        val cityLeft = ElementGroup.city { left }
        val cityRight = ElementGroup.city { right }
        add(
            Element.Field,
            ElementGroup.field(cityLeft, cityRight) { top + bottom },
        )
        add(
            Element.City,
            cityLeft,
            cityRight,
        )
    },
)
