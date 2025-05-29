package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field

val G = Tile(
    name = "G",
    edges = Edges(top = Field, right = City, bottom = Field, left = City),
    elements = elements {
        val city = city { left + right }
        field(city) { top }
        field(city) { bottom }
    },
)
