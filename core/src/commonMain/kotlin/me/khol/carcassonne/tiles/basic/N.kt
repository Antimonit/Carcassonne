package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object N {

    val city = City { top + right }
    val field = Field(city) { left + bottom }

    val tile = Tile(
        name = "N",
        edges = Edges(top = City, right = City, bottom = Field, left = Field),
        elements = elements {
            add(city)
            add(field)
        },
    )
}

object N_G {

    val garden = Garden
    val city = City { top + right }
    val field = Field(city) { left + bottom }

    val tile = Tile(
        name = "N_G",
        edges = Edges(top = City, right = City, bottom = Field, left = Field),
        elements = elements {
            add(garden)
            add(city)
            add(field)
        },
    )
}
