package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object T {

    val city = City { top + right + left }
    val fieldLeft = Field(city) { bottomLeft }
    val fieldRight = Field(city) { bottomRight }
    val road = Road { bottom }

    val tile = Tile(
        name = "T",
        edges = Edges(top = City, right = City, bottom = Road, left = City),
        elements = elements {
            add(city)
            add(fieldLeft)
            add(fieldRight)
            add(road)
        },
    )
}
