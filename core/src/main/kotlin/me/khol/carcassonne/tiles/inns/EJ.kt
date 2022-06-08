package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EJ = Tile(
    name = "EJ",
    edges = Edges(top = City, right = Field, bottom = Road, left = Field),
    elements = elements {
        val city = Positions.city { top }
        add(
            Element.City,
            city,
        )
        add(
            Element.Road,
            Positions.road { bottom },
        )
        add(
            Element.Field,
            Positions.field(city) { right + bottomRight },
            Positions.field(city) { left + bottomLeft },
        )
    },
)
