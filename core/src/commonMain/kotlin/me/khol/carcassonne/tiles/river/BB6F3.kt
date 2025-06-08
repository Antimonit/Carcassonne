package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.city
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.river

val BB6F3 = Tile(
    name = "BB6F3",
    edges = Edges(top = City, right = River, bottom = City, left = River),
    elements = elements {
        val cityTop = city { top }
        val cityBottom = city { bottom }
        field(cityTop) { leftTop + rightTop }
        field(cityBottom) { rightBottom + leftBottom }
        river { left + right }
    },
)

