package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EI = Tile(
    name = "EI",
    edges = Edges(top = City, right = Road, bottom = City, left = Road),
    elements = elements {
        val cityTop = ElementGroup.city { top }
        val cityBottom = ElementGroup.city { bottom }
        add(
            Element.City,
            cityTop,
            cityBottom,
        )
        add(
            Element.Road,
            ElementGroup.road { left },
            ElementGroup.road { right },
        )
        add(
            Element.Field,
            ElementGroup.field(cityTop) { leftTop },
            ElementGroup.field(cityTop) { rightTop },
            ElementGroup.field(cityBottom) { leftBottom },
            ElementGroup.field(cityBottom) { rightBottom },
        )
    },
)
