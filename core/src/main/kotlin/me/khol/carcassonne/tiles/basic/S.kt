package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val S = Tile(
    name = "S",
    edges = Edges(top = City, right = City, bottom = Road, left = City),
    elements = elements {
        val city = city(Boon.City.CoatOfArms) { top + right + left }
        field(city) { bottomLeft }
        field(city) { bottomRight }
        road { bottom }
    },
)
