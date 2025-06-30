package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object J {

    val city = City { top }
    val fieldTop = Field(city) { left + bottomLeft + rightTop }
    val fieldBottom = Field { bottomRight + rightBottom }
    val road = Road { bottom + right }

    val tile = Tile(
        name = "J",
        edges = Edges(top = City, right = Road, bottom = Road, left = Field),
        elements = elements {
            add(city)
            add(fieldTop)
            add(fieldBottom)
            add(road)
        },
    )
}
