package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.garden

val E = Tile(
    name = "E",
    edges = Edges(top = City, right = Field, bottom = Field, left = Field),
    elements = elements {
        val city = city { top }
        field(city) { left + right + bottom }
    },
)

val E_G = Tile(
    name = "E_G",
    edges = Edges(top = City, right = Field, bottom = Field, left = Field),
    elements = elements {
        garden()
        val city = city { top }
        field(city) { left + right + bottom }
    },
)
