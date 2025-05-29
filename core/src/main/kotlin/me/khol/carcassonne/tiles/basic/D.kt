package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val D = Tile(
    name = "D",
    edges = Edges(top = City, right = Road, bottom = Field, left = Road),
    elements = elements {
        val city = city { top }
        field(city) { leftTop + rightTop }
        field { leftBottom + rightBottom + bottom }
        road { left + right }
    },
)
