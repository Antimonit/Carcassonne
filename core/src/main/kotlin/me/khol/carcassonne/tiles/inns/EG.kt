package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EG = Tile(
    name = "EG",
    edges = Edges(top = Field, right = Field, bottom = Field, left = City),
    elements = elements {
        val city = Positions.city { left }
        add(
            Element.City,
            city,
        )
        add(
            Element.Field,
            Positions.field(city) { top },
            Positions.field(city) { right + bottom },
        )
    },
)
