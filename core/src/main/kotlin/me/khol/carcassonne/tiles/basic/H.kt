package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.garden

val H = Tile(
    name = "H",
    edges = Edges(top = City, right = Field, bottom = City, left = Field),
    elements = elements {
        val cityTop = city { top }
        val cityBottom = city { bottom }
        field(cityTop, cityBottom) { left + right }
    },
)

val H_G = Tile(
    name = "H_G",
    edges = Edges(top = City, right = Field, bottom = City, left = Field),
    elements = elements {
        garden()
        val cityTop = city { top }
        val cityBottom = city { bottom }
        field(cityTop, cityBottom) { left + right }
    },
)
