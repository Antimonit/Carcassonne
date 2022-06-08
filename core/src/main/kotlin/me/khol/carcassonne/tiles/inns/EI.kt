package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EI = Tile(
    name = "EI",
    edges = Edges(top = City, right = Road, bottom = City, left = Road),
    elements = elements {
        val cityTop = Positions.city { top }
        val cityBottom = Positions.city { bottom }
        add(
            Element.City,
            cityTop,
            cityBottom,
        )
        add(
            Element.Road,
            Positions.road { left },
            Positions.road { right },
        )
        add(
            Element.Field,
            Positions.field(cityTop) { leftTop },
            Positions.field(cityTop) { rightTop },
            Positions.field(cityBottom) { leftBottom },
            Positions.field(cityBottom) { rightBottom },
        )
    },
)
