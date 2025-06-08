package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val P = Tile(
    name = "P",
    edges = Edges(top = City, right = Road, bottom = Road, left = City),
    elements = elements {
        val city = city { top + left }
        field(city) { rightTop + bottomLeft }
        field { rightBottom + bottomRight }
        road { right + bottom }
    },
)
