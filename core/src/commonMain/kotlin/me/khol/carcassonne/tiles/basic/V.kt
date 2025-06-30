package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object V {

    val road = Road { bottom + left }
    val fieldBottom = Field { leftBottom + bottomLeft }
    val fieldTop = Field { leftTop + top + right + bottomRight }

    val tile = Tile(
        name = "V",
        edges = Edges(top = Field, right = Field, bottom = Road, left = Road),
        elements = elements {
            add(road)
            add(fieldBottom)
            add(fieldTop)
        },
    )
}

object V_G {

    val garden = Garden
    val road = Road { bottom + left }
    val fieldBottom = Field { leftBottom + bottomLeft }
    val fieldTop = Field { leftTop + top + right + bottomRight }

    val tile = Tile(
        name = "V_G",
        edges = Edges(top = Field, right = Field, bottom = Road, left = Road),
        elements = elements {
            add(garden)
            add(road)
            add(fieldBottom)
            add(fieldTop)
        },
    )
}
