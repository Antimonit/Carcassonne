package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.cropCircle
import me.khol.carcassonne.elements

val Korn6 = Tile(
    name = "Korn6",
    edges = Edges(top = City, right = City, bottom = City, left = City),
    elements = elements {
        city { left + top }
        city { right + bottom }
        cropCircle()
    },
)
