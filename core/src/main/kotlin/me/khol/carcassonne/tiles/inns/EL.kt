package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EL = Tile(
    name = "EL",
    edges = Edges(top = City, right = Road, bottom = Road, left = City),
    elements = elements {
        val city = ElementGroup.city(Boon.City.CoatOfArms) { left + top }
        add(
            Element.City,
            city,
        )
        add(
            Element.Road,
            ElementGroup.road(Boon.Road.Inn) { right + bottom },
        )
        add(
            Element.Field,
            ElementGroup.field(city) { rightTop + bottomLeft },
            ElementGroup.field { rightBottom + bottomRight },
        )
    },
)
