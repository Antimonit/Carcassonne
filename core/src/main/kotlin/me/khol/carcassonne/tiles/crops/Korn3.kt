package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val Korn3 = Tile(
    name = "Korn3",
    edges = Edges(top = City, right = Field, bottom = Road, left = Field),
    elements = elements {
        add(
            Element.Field,
            Positions.splitEdges { Left + Right + Bottom },
        )
        add(
            Element.City,
            Positions.Edge.Top,
        )
        add(
            Element.Road,
            Positions.Edge.Bottom,
        )
        add(
            Element.CropCircle,
            Positions.Center,
        )
    },
)
