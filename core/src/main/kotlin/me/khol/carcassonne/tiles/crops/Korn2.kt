package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
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
            ElementGroup.field { leftBottom + bottomLeft },
            ElementGroup.field { leftTop + topLeft + rightBottom + bottomRight },
            ElementGroup.field { topRight + rightTop },
        )
        add(
            Element.Road,
            ElementGroup.road { top },
            ElementGroup.road { right },
            ElementGroup.road { left + bottom },
        )
        add(
            Element.CropCircle,
            ElementGroup.Center,
        )
    },
)
