package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val C = Tile(
    name = "C",
    edges = Edges(top = City, right = City, bottom = City, left = City),
    elements = elements {
        add(
            Element.City,
            Positions.city(Boon.City.CoatOfArms) { all },
        )
        add(
            Element.Monastery,
            Positions.Center,
        )
    },
)
