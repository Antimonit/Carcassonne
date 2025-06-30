package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object K {

    val city = City { top }
    val fieldTop = Field(city) { right + bottomRight + leftTop }
    val fieldBottom = Field { bottomLeft + leftBottom }
    val road = Road { bottom + left }

    val tile = Tile(
        name = "K",
        edges = Edges(top = City, right = Field, bottom = Road, left = Road),
        elements = elements {
            add(city)
            add(fieldTop)
            add(fieldBottom)
            add(road)
        },
    )
}
