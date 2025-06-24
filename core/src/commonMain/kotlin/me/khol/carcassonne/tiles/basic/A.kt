package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.ElementGroup.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object A {
    val field = Field { all }
    val road = Road { bottom }
    val monastery = Monastery

    val tile = Tile(
        name = "A",
        edges = Edges(top = Field, right = Field, bottom = Road, left = Field),
        elements = elements {
            add(field)
            add(road)
            add(monastery)
        },
    )
}
