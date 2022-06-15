package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F2 = Tile(
    name = "BB6F2",
    edges = Edges(top = City, right = River, bottom = Road, left = River),
    elements = elements {
        val city = ElementGroup.city { top }
        add(
            Element.Field,
            ElementGroup.field(city) { leftTop },
            ElementGroup.field(city) { rightTop },
            ElementGroup.field { rightBottom + bottomRight },
            ElementGroup.field { bottomLeft + leftBottom },
        )
        add(
            Element.River,
            ElementGroup.edges { left + right },
        )
        add(
            Element.Road,
            ElementGroup.road { bottom },
        )
        add(
            Element.City,
            city,
        )
    },
)
