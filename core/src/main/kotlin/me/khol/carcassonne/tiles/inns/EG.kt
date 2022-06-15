package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EG = Tile(
    name = "EG",
    edges = Edges(top = Field, right = Field, bottom = Field, left = City),
    elements = elements {
        val city = ElementGroup.city { left }
        add(
            Element.City,
            city,
        )
        add(
            Element.Field,
            ElementGroup.field(city) { top },
            ElementGroup.field(city) { right + bottom },
        )
    },
)
