package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object E {

    val city = City { top }
    val field = Field(city) { left + right + bottom }

    val tile = Tile(
        name = "E",
        edges = Edges(top = City, right = Field, bottom = Field, left = Field),
        elements = elements {
            add(city)
            add(field)
        },
    )
}

object E_G {

    val garden = Garden
    val city = City { top }
    val field = Field(city) { left + right + bottom }

    val tile = Tile(
        name = "E_G",
        edges = Edges(top = City, right = Field, bottom = Field, left = Field),
        elements = elements {
            add(garden)
            add(city)
            add(field)
        },
    )
}
