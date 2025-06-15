package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.river

val RiverE = Tile(
    name = "RiverE",
    edges = Edges(top = City, right = City, bottom = River, left = River),
    elements = elements {
        val city = city { top + right }
        field(city) { leftTop + bottomRight }
        field { leftBottom + bottomLeft }
        river { left + bottom }
    },
)
