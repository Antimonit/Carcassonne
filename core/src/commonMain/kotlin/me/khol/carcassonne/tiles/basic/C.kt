package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.monastery

val C = Tile(
    name = "C",
    edges = Edges(top = City, right = City, bottom = City, left = City),
    elements = elements {
        city(Boon.City.CoatOfArms) { all }
        monastery()
    },
)
