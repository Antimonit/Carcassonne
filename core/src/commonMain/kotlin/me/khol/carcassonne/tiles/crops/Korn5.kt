package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.cropCircle
import me.khol.carcassonne.elements
import me.khol.carcassonne.field

val Korn5 = Tile(
    name = "Korn5",
    edges = Edges(top = City, right = Field, bottom = City, left = Field),
    elements = elements {
        val cityTop = city { top }
        val cityBottom = city { bottom }
        field(cityTop, cityBottom) { left }
        field(cityTop, cityBottom) { right }
        cropCircle()
    },
)
