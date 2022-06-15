package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val K = Tile(
    name = "K",
    edges = Edges(top = City, right = Field, bottom = Road, left = Road),
    elements = elements {
        val city = Positions.city { top }
        add(
            Element.Field,
            Positions.field(city) { right + bottomRight + leftTop },
            Positions.field { bottomLeft + leftBottom },
        )
        add(
            Element.Road,
            Positions.road { bottom + left },
        )
        add(
            Element.City,
            city,
        )
    },
)
