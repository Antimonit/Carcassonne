package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
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
            ElementGroup.field { leftTop + top + rightTop },
            ElementGroup.field { leftBottom + bottomLeft },
            ElementGroup.field { rightBottom + bottomRight },
        )
        add(
            Element.Road,
            ElementGroup.road { right },
            ElementGroup.road { left + bottom },
        )
        add(
            Element.CropCircle,
            ElementGroup.Center,
        )
    },
)
