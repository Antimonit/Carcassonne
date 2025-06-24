package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.ElementGroup.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object I {

    val cityLeft = City { left }
    val cityTop = City { top }
    val field = Field(cityLeft, cityTop) { right + bottom }

    val tile = Tile(
        name = "I",
        edges = Edges(top = City, right = Field, bottom = Field, left = City),
        elements = elements {
            add(cityLeft)
            add(cityTop)
            add(field)
        },
    )
}

object I_G {

    val garden = Garden
    val cityLeft = City { left }
    val cityTop = City { top }
    val field = Field(cityLeft, cityTop) { right + bottom }

    val tile = Tile(
        name = "I_G",
        edges = Edges(top = City, right = Field, bottom = Field, left = City),
        elements = elements {
            add(garden)
            add(cityLeft)
            add(cityTop)
            add(field)
        },
    )
}