package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field

val InnsP = Tile(
    name = "InnsP",
    edges = Edges(top = City, right = City, bottom = Field, left = City),
    elements = elements {
        val cityTopRight = city(Boon.City.CoatOfArms) { top + right }
        val cityLeft = city { left }
        field(cityTopRight, cityLeft) { bottom }
    },
)
