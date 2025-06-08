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

val EQ = Tile(
    name = "EQ",
    edges = Edges(top = Road, right = City, bottom = Road, left = City),
    elements = elements {
        val city = city(Boon.City.CoatOfArms) { left + right }
        field(city) { topLeft }
        field(city) { topRight }
        field(city) { bottomLeft }
        field(city) { bottomRight }
        road { top }
        road { bottom }
    },
)
