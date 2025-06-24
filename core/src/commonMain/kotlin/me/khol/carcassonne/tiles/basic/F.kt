package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.ElementGroup.*
import me.khol.carcassonne.Boon
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object F {

    val city = City(Boon.City.CoatOfArms) { left + right }
    val fieldTop = Field(city) { top }
    val fieldBottom = Field(city) { bottom }

    val tile = Tile(
        name = "F",
        edges = Edges(top = Field, right = City, bottom = Field, left = City),
        elements = elements {
            add(city)
            add(fieldTop)
            add(fieldBottom)
        },
    )
}
