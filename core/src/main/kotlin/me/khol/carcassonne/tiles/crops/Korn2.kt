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
            Positions.field { leftBottom + bottomLeft },
            Positions.field { leftTop + topLeft + rightBottom + bottomRight },
            Positions.field { topRight + rightTop },
        )
        add(
            Element.Road,
            Positions.road { top },
            Positions.road { right },
            Positions.road { left + bottom },
        )
        add(
            Element.CropCircle,
            Positions.Center,
        )
    },
)
