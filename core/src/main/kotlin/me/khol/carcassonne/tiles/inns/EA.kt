package me.khol.carcassonne.tiles.inns

import me.khol.carcassonne.Boon
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val EA = Tile(
    name = "EA",
    edges = Edges(top = Field, right = Field, bottom = Road, left = Road),
    elements = elements {
        add(
            Element.Road,
            ElementGroup.road(Boon.Road.Inn) { bottom + left },
        )
        add(
            Element.Field,
            ElementGroup.field { leftBottom + bottomLeft },
            ElementGroup.field { leftTop + top + right + bottomRight },
        )
    },
)
