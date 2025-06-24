package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.ElementGroup.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object L {

    val city = City { top }
    val fieldTop = Field(city) { rightTop + leftTop }
    val fieldLeft = Field { bottomLeft + leftBottom }
    val fieldRight = Field { bottomRight + rightBottom }
    val roadLeft = Road { left }
    val roadBottom = Road { bottom }
    val roadRight = Road { right }

    val tile = Tile(
        name = "L",
        edges = Edges(top = City, right = Road, bottom = Road, left = Road),
        elements = elements {
            add(city)
            add(fieldTop)
            add(fieldLeft)
            add(fieldRight)
            add(roadLeft)
            add(roadBottom)
            add(roadRight)
        },
    )
}
