package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object R {

    val city = City { top + right + left }
    val field = Field(city) { bottom }

    val tile = Tile(
        name = "R",
        edges = Edges(top = City, right = City, bottom = Field, left = City),
        elements = elements {
            add(city)
            add(field)
        },
    )
}

object R_G {

    val garden = Garden
    val city = City { top + right + left }
    val field = Field(city) { bottom }

    val tile = Tile(
        name = "R_G",
        edges = Edges(top = City, right = City, bottom = Field, left = City),
        elements = elements {
            add(garden)
            add(city)
            add(field)
        },
    )
}
