package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field

val H = Tile(
    name = "H",
    edges = Edges(top = Field, right = City, bottom = Field, left = City),
    elements = elements {
        val cityLeft = city { left }
        val cityRight = city { right }
        field(cityLeft, cityRight) { top + bottom }
    },
)
