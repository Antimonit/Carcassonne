package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EM = Tile(
    name = "EM",
    edges = Edges(top = City, right = Field, bottom = Road, left = Road),
    elements = elements {
        val city = Positions.city { top }
        add(
            Element.City,
            city,
        )
        add(
            Element.Road,
            Positions.road(Boon.Road.Inn) { bottom + left },
        )
        add(
            Element.Field,
            Positions.field(city) { right + bottomRight + leftTop },
            Positions.field { bottomLeft + leftBottom },
        )
    },
)
