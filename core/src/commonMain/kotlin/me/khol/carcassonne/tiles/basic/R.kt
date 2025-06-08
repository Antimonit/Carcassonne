package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.garden

val R = Tile(
    name = "R",
    edges = Edges(top = City, right = City, bottom = Field, left = City),
    elements = elements {
        val city = city { top + right + left }
        field(city) { bottom }
    },
)

val R_G = Tile(
    name = "R_G",
    edges = Edges(top = City, right = City, bottom = Field, left = City),
    elements = elements {
        garden()
        val city = city { top + right + left }
        field(city) { bottom }
    },
)
