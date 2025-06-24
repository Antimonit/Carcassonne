package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.ElementGroup.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object H {

    val cityTop = City { top }
    val cityBottom = City { bottom }
    val field = Field(cityTop, cityBottom) { left + right }

    val tile = Tile(
        name = "H",
        edges = Edges(top = City, right = Field, bottom = City, left = Field),
        elements = elements {
            add(cityTop)
            add(cityBottom)
            add(field)
        },
    )
}

object H_G {

    val garden = Garden
    val cityTop = City { top }
    val cityBottom = City { bottom }
    val field = Field(cityTop, cityBottom) { left + right }

    val tile = Tile(
        name = "H_G",
        edges = Edges(top = City, right = Field, bottom = City, left = Field),
        elements = elements {
            add(garden)
            add(cityTop)
            add(cityBottom)
            add(field)
        },
    )
}