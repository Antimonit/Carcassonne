package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.ElementGroup.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object W {

    val roadRight = Road { right }
    val roadBottom = Road { bottom }
    val roadLeft = Road { left }
    val fieldTop = Field { leftTop + top + rightTop }
    val fieldRight = Field { rightBottom + bottomRight }
    val fieldLeft = Field { leftBottom + bottomLeft }

    val tile = Tile(
        name = "W",
        edges = Edges(top = Field, right = Road, bottom = Road, left = Road),
        elements = elements {
            add(roadRight)
            add(roadBottom)
            add(roadLeft)
            add(fieldTop)
            add(fieldRight)
            add(fieldLeft)
        },
    )
}
