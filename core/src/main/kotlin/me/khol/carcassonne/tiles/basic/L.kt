package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val L = Tile(
    name = "L",
    edges = Edges(top = City, right = Road, bottom = Road, left = Road),
    elements = elements {
        val city = Positions.city { top }
        add(
            Element.Field,
            Positions.field(city) { rightTop + leftTop },
            Positions.field { bottomLeft + leftBottom },
            Positions.field { bottomRight + rightBottom },
        )
        add(
            Element.Road,
            Positions.road { left },
            Positions.road { bottom },
            Positions.road { right },
        )
        add(
            Element.City,
            city,
        )
    },
)
