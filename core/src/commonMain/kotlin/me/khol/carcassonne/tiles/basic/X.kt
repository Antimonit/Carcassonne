package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.ElementGroup.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object X {

    val roadTop = Road { top }
    val roadRight = Road { right }
    val roadBottom = Road { bottom }
    val roadLeft = Road { left }
    val fieldTopLeft = Field { leftTop + topLeft }
    val fieldTopRight = Field { topRight + rightTop }
    val fieldBottomLeft = Field { leftBottom + bottomLeft }
    val fieldBottomRight = Field { rightBottom + bottomRight }

    val tile = Tile(
        name = "X",
        edges = Edges(top = Road, right = Road, bottom = Road, left = Road),
        elements = elements {
            add(roadTop)
            add(roadRight)
            add(roadBottom)
            add(roadLeft)
            add(fieldTopLeft)
            add(fieldTopRight)
            add(fieldBottomLeft)
            add(fieldBottomRight)
        },
    )
}
