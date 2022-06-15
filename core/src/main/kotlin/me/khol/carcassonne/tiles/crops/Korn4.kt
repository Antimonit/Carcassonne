package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val Korn4 = Tile(
    name = "Korn4",
    edges = Edges(top = City, right = Road, bottom = Field, left = Road),
    elements = elements {
        val city = ElementGroup.city { top }
        add(
            Element.Field,
            ElementGroup.field(city) { leftTop + rightTop },
            ElementGroup.field { leftBottom + rightBottom + bottom },
        )
        add(
            Element.City,
            city,
        )
        add(
            Element.Road,
            ElementGroup.road { left },
            ElementGroup.road { right },
        )
        add(
            Element.CropCircle,
            ElementGroup.Center,
        )
    },
)
