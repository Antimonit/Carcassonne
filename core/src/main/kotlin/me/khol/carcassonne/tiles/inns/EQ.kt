package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EQ = Tile(
    name = "EQ",
    edges = Edges(top = Road, right = City, bottom = Road, left = City),
    elements = elements {
        val city = Positions.city(Boon.City.CoatOfArms) { left + right }
        add(
            Element.Field,
            Positions.field(city) { topLeft },
            Positions.field(city) { topRight },
            Positions.field(city) { bottomLeft },
            Positions.field(city) { bottomRight },
        )
        add(
            Element.City,
            city,
        )
        add(
            Element.Road,
            Positions.road { top },
            Positions.road { bottom },
        )
    },
)
