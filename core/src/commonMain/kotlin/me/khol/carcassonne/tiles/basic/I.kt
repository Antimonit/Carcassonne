package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.garden

val I = Tile(
    name = "I",
    edges = Edges(top = City, right = Field, bottom = Field, left = City),
    elements = elements {
        val cityLeft = city { left }
        val cityTop = city { top }
        field(cityLeft, cityTop) { right + bottom }
    },
)

val I_G = Tile(
    name = "I_G",
    edges = Edges(top = City, right = Field, bottom = Field, left = City),
    elements = elements {
        garden()
        val cityLeft = city { left }
        val cityTop = city { top }
        field(cityLeft, cityTop) { right + bottom }
    },
)
