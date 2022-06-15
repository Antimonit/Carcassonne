package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EQ = Tile(
    name = "EQ",
    edges = Edges(top = Road, right = City, bottom = Road, left = City),
    elements = elements {
        val city = ElementGroup.city(Boon.City.CoatOfArms) { left + right }
        add(
            Element.Field,
            ElementGroup.field(city) { topLeft },
            ElementGroup.field(city) { topRight },
            ElementGroup.field(city) { bottomLeft },
            ElementGroup.field(city) { bottomRight },
        )
        add(
            Element.City,
            city,
        )
        add(
            Element.Road,
            ElementGroup.road { top },
            ElementGroup.road { bottom },
        )
    },
)
