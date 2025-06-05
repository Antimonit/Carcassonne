package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.garden

val EH = Tile(
    name = "EH",
    edges = Edges(top = City, right = City, bottom = City, left = City),
    elements = elements {
        val cityTop = city { top }
        val cityRight = city { right }
        val cityBottom = city { bottom }
        val cityLeft = city { left }
        field(cityTop, cityRight, cityBottom, cityLeft) { none }
        garden()
    },
)
