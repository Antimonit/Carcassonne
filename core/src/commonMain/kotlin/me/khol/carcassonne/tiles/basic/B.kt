package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element.*
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object B {
    val field = Field { all }
    val monastery = Monastery

    val tile = Tile(
        name = "B",
        edges = Edges(top = Field, right = Field, bottom = Field, left = Field),
        elements = elements {
            add(field)
            add(monastery)
        },
    )
}
