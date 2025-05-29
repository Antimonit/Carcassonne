package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val L = Tile(
    name = "L",
    edges = Edges(top = City, right = Road, bottom = Road, left = Road),
    elements = elements {
        val city = city { top }
        field(city) { rightTop + leftTop }
        field { bottomLeft + leftBottom }
        field { bottomRight + rightBottom }
        road { left }
        road { bottom }
        road { right }
    },
)
