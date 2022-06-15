package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val L = Tile(
    name = "L",
    edges = Edges(top = City, right = Road, bottom = Road, left = Road),
    elements = elements {
        val city = ElementGroup.city { top }
        add(
            Element.Field,
            ElementGroup.field(city) { rightTop + leftTop },
            ElementGroup.field { bottomLeft + leftBottom },
            ElementGroup.field { bottomRight + rightBottom },
        )
        add(
            Element.Road,
            ElementGroup.road { left },
            ElementGroup.road { bottom },
            ElementGroup.road { right },
        )
        add(
            Element.City,
            city,
        )
    },
)
