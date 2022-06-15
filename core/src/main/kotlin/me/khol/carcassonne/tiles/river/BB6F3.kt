package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F3 = Tile(
    name = "BB6F3",
    edges = Edges(top = City, right = River, bottom = City, left = River),
    elements = elements {
        val cityTop = ElementGroup.city { top }
        val cityBottom = ElementGroup.city { bottom }
        add(
            Element.Field,
            ElementGroup.field(cityTop) { leftTop + rightTop },
            ElementGroup.field(cityBottom) { rightBottom + leftBottom },
        )
        add(
            Element.River,
            ElementGroup.edges { left + right },
        )
        add(
            Element.City,
            cityTop,
            cityBottom,
        )
    },
)

