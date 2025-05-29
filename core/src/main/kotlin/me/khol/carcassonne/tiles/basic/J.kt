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

val J = Tile(
    name = "J",
    edges = Edges(top = City, right = Road, bottom = Road, left = Field),
    elements = elements {
        val city = city { top }
        field(city) { left + bottomLeft + rightTop }
        field { bottomRight + rightBottom }
        road { bottom + right }
    },
)
