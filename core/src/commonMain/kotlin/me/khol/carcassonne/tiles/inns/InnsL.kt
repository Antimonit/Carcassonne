package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val InnsL = Tile(
    name = "InnsL",
    edges = Edges(top = City, right = Road, bottom = Road, left = City),
    elements = elements {
        val city = city(Boon.City.CoatOfArms) { left + top }
        road(Boon.Road.Inn) { right + bottom }
        field(city) { rightTop + bottomLeft }
        field { rightBottom + bottomRight }
    },
)
