package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.ElementGroup.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object U {

    val road = Road { top + bottom }
    val fieldLeft = Field { topLeft + left + bottomLeft }
    val fieldRight = Field { topRight + right + bottomRight }

    val tile = Tile(
        name = "U",
        edges = Edges(top = Road, right = Field, bottom = Road, left = Field),
        elements = elements {
            add(road)
            add(fieldLeft)
            add(fieldRight)
        },
    )
}

object U_G {

    val garden = Garden
    val road = Road { top + bottom }
    val fieldLeft = Field { topLeft + left + bottomLeft }
    val fieldRight = Field { topRight + right + bottomRight }

    val tile = Tile(
        name = "U_G",
        edges = Edges(top = Road, right = Field, bottom = Road, left = Field),
        elements = elements {
            add(garden)
            add(road)
            add(fieldLeft)
            add(fieldRight)
        },
    )
}
