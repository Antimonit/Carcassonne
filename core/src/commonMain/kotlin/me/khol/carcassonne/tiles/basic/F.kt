package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field

val F = Tile(
    name = "F",
    edges = Edges(top = Field, right = City, bottom = Field, left = City),
    elements = elements {
        val city = city(Boon.City.CoatOfArms) { left + right }
        field(city) { top }
        field(city) { bottom }
    },
)
