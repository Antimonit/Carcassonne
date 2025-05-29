package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field

val N = Tile(
    name = "N",
    edges = Edges(top = City, right = City, bottom = Field, left = Field),
    elements = elements {
        val city = city { top + right }
        field(city) { left + bottom }
    },
)
