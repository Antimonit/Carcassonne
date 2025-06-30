package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object D {
    val city = City { top }
    val fieldTop = Field(city) { leftTop + rightTop }
    val fieldBottom = Field { leftBottom + rightBottom + bottom }
    val road = Road { left + right }

    val tile = Tile(
        name = "D",
        edges = Edges(top = City, right = Road, bottom = Field, left = Road),
        elements = elements {
            add(city)
            add(fieldTop)
            add(fieldBottom)
            add(road)
        },
    )
}
