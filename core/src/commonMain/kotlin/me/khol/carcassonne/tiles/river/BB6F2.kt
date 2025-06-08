package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.river
import me.khol.carcassonne.road

val BB6F2 = Tile(
    name = "BB6F2",
    edges = Edges(top = City, right = River, bottom = Road, left = River),
    elements = elements {
        val city = city { top }
        field(city) { leftTop }
        field(city) { rightTop }
        field { rightBottom + bottomRight }
        field { bottomLeft + leftBottom }
        river { left + right }
        road { bottom }
    },
)
