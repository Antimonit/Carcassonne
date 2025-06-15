package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field

val InnsG = Tile(
    name = "InnsG",
    edges = Edges(top = Field, right = Field, bottom = Field, left = City),
    elements = elements {
        val city = city { left }
        field(city) { top }
        field(city) { right + bottom }
    },
)
