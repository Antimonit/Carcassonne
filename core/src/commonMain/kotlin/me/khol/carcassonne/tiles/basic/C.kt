package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.ElementGroup.*
import me.khol.carcassonne.Boon
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.*
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.add
import me.khol.carcassonne.elements

object C {

    val city = City(Boon.City.CoatOfArms) { all }
    val monastery = Monastery

    val tile = Tile(
        name = "C",
        edges = Edges(top = City, right = City, bottom = City, left = City),
        elements = elements {
            add(city)
            add(monastery)
        },
    )
}
