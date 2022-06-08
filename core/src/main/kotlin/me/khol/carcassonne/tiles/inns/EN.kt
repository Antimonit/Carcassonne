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

val EN = Tile(
    name = "EN",
    edges = Edges(top = City, right = Field, bottom = Road, left = City),
    elements = elements {
        val city = Positions.city { left + top }
        add(
            Element.City,
            city,
        )
        add(
            Element.Road,
            Positions.road(Boon.Road.Inn) { bottom }
        )
        add(
            Element.Field,
            Positions.field(city) { right + bottomRight },
            Positions.field(city) { bottomLeft },
        )
    },
)
