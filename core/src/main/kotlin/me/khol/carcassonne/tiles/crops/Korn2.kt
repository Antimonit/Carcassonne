package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val Korn2 = Tile(
    name = "Korn2",
    edges = Edges(top = Road, right = Road, bottom = Road, left = Road),
    elements = elements {
        add(
            Element.Field,
            Positions.splitEdges { LeftBottom + BottomLeft },
            Positions.splitEdges { LeftTop + TopLeft + RightBottom + BottomRight },
            Positions.splitEdges { TopRight + RightTop },
        )
        add(
            Element.Road,
            Positions.Edge.Top,
            Positions.Edge.Right,
            Positions.edges { Left + Bottom },
        )
        add(
            Element.CropCircle,
            Positions.Center,
        )
    },
)
