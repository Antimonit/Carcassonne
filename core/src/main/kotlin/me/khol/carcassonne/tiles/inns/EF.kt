package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EF = Tile(
    name = "EF",
    edges = Edges(top = City, right = Road, bottom = Field, left = City),
    elements = elements {
        val city = Positions.city { left + top }
        add(
            Element.City,
            city,
        )
        add(
            Element.Road,
            Positions.road { right },
        )
        add(
            Element.Field,
            Positions.field(city) { rightTop },
            Positions.field(city) { rightBottom + bottom },
        )
    },
)
