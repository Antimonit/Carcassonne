package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Boon
import me.khol.carcassonne.ElementGroup.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object Q {

    val city = City(Boon.City.CoatOfArms) { top + right + left }
    val field = Field(city) { bottom }

    val tile = Tile(
        name = "Q",
        edges = Edges(top = City, right = City, bottom = Field, left = City),
        elements = elements {
            add(city)
            add(field)
        },
    )
}
