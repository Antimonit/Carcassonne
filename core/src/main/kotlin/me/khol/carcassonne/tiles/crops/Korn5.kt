package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val Korn5 = Tile(
    name = "Korn5",
    edges = Edges(top = City, right = Field, bottom = City, left = Field),
    elements = elements {
        add(
            Element.Field,
            Positions.SplitEdge.Left,
            Positions.SplitEdge.Right,
        )
        add(
            Element.City,
            Positions.Edge.Top,
            Positions.Edge.Bottom,
        )
        add(
            Element.CropCircle,
            Positions.Center,
        )
    },
)
