package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.ElementGroup.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object P {

    val city = City { top + left }
    val fieldTopLeft = Field(city) { rightTop + bottomLeft }
    val fieldBottomRight = Field { rightBottom + bottomRight }
    val road = Road { right + bottom }

    val tile = Tile(
        name = "P",
        edges = Edges(top = City, right = Road, bottom = Road, left = City),
        elements = elements {
            add(city)
            add(fieldTopLeft)
            add(fieldBottomRight)
            add(road)
        },
    )
}
