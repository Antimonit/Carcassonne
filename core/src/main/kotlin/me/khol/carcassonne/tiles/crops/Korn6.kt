package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val Korn6 = Tile(
    name = "Korn6",
    edges = Edges(top = City, right = City, bottom = City, left = City),
    elements = elements {
        add(
            Element.City,
            Positions.edges { Left + Top },
            Positions.edges { Right + Bottom },
        )
        add(
            Element.CropCircle,
            Positions.Center,
        )
    },
)
