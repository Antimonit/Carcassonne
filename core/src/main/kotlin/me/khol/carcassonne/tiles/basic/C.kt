package me.khol.carcassonne.tiles.basic

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
            Positions.Edge.All,
        )
        add(
            Element.Monastery,
            Positions.Center,
        )
    },
)
// TODO: Add Coat of Arms