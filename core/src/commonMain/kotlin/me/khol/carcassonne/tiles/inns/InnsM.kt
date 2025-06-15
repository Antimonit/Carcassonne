package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val InnsM = Tile(
    name = "InnsM",
    edges = Edges(top = City, right = Field, bottom = Road, left = Road),
    elements = elements {
        val city = city { top }
        road(Boon.Road.Inn) { bottom + left }
        field(city) { right + bottomRight + leftTop }
        field { bottomLeft + leftBottom }
    },
)
