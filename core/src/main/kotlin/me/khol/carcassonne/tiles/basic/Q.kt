package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val Q = Tile(
    name = "Q",
    edges = Edges(top = City, right = City, bottom = Field, left = City),
    elements = elements {
        val city = ElementGroup.city(Boon.City.CoatOfArms) { top + right + left }
        add(
            Element.Field,
            ElementGroup.field(city) { bottom },
        )
        add(
            Element.City,
            city,
        )
    },
)
