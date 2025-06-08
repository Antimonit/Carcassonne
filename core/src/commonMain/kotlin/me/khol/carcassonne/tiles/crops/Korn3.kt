package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.cropCircle
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.road

val Korn3 = Tile(
    name = "Korn3",
    edges = Edges(top = City, right = Field, bottom = Road, left = Field),
    elements = elements {
        val city = city { top }
        field(city) { left + right + bottom }
        road { bottom }
        cropCircle()
    },
)
