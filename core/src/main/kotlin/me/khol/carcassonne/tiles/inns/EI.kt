package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val EI = Tile(
    name = "EI",
    edges = Edges(top = City, right = Road, bottom = City, left = Road),
    elements = elements {
        val cityTop = city { top }
        val cityBottom = city { bottom }
        road { left }
        road { right }
        field(cityTop) { leftTop }
        field(cityTop) { rightTop }
        field(cityBottom) { leftBottom }
        field(cityBottom) { rightBottom }
    },
)
