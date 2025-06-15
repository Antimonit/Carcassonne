package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val InnsF = Tile(
    name = "InnsF",
    edges = Edges(top = City, right = Road, bottom = Field, left = City),
    elements = elements {
        val city = city { left + top }
        road { right }
        field(city) { rightTop }
        field(city) { rightBottom + bottom }
    },
)
