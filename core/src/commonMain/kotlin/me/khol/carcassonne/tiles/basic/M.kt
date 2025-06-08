package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.garden

val M = Tile(
    name = "M",
    edges = Edges(top = City, right = City, bottom = Field, left = Field),
    elements = elements {
        val city = city(Boon.City.CoatOfArms) { top + right }
        field(city) { left + bottom }
    },
)

val M_G = Tile(
    name = "M_G",
    edges = Edges(top = City, right = City, bottom = Field, left = Field),
    elements = elements {
        garden()
        val city = city(Boon.City.CoatOfArms) { top + right }
        field(city) { left + bottom }
    },
)
