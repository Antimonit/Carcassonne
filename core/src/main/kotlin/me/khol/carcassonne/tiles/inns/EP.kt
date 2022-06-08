package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EP = Tile(
    name = "EP",
    edges = Edges(top = City, right = City, bottom = Field, left = City),
    elements = elements {
        val cityTopRight = Positions.city(Boon.City.CoatOfArms) { top + right }
        val cityLeft = Positions.city { left }
        add(
            Element.City,
            cityTopRight,
            cityLeft,
        )
        add(
            Element.Field,
            Positions.field(cityTopRight, cityLeft) { bottom },
        )
    },
)
