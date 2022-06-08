package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val Korn1 = Tile(
    name = "Korn1",
    edges = Edges(top = Field, right = Road, bottom = Road, left = Road),
    elements = elements {
        add(
            Element.Field,
            Positions.splitEdges { LeftTop + Top + RightTop },
            Positions.splitEdges { LeftBottom + BottomLeft },
            Positions.splitEdges { RightBottom + BottomRight },
        )
        add(
            Element.Road,
            Positions.Edge.Right,
            Positions.edges { Left + Bottom },
        )
        add(
            Element.CropCircle,
            Positions.Center,
        )
    },
)
