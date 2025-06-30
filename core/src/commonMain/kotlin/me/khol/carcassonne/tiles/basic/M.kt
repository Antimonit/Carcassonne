package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Element.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object M {

    val city = City(Boon.City.CoatOfArms) { top + right }
    val field = Field(city) { left + bottom }

    val tile = Tile(
        name = "M",
        edges = Edges(top = City, right = City, bottom = Field, left = Field),
        elements = elements {
            add(city)
            add(field)
        },
    )
}

object M_G {

    val garden = Garden
    val city = City(Boon.City.CoatOfArms) { top + right }
    val field = Field(city) { left + bottom }

    val tile = Tile(
        name = "M_G",
        edges = Edges(top = City, right = City, bottom = Field, left = Field),
        elements = elements {
            add(garden)
            add(city)
            add(field)
        },
    )
}
