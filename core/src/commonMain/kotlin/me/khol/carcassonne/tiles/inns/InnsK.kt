package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements

val InnsK = Tile(
    name = "InnsK",
    edges = Edges(top = City, right = City, bottom = City, left = City),
    elements = elements {
        city(Boon.City.Cathedral) { all }
    },
)
