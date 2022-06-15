package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val Korn5 = Tile(
    name = "Korn5",
    edges = Edges(top = City, right = Field, bottom = City, left = Field),
    elements = elements {
        val cityTop = ElementGroup.city { top }
        val cityBottom = ElementGroup.city { bottom }
        add(
            Element.Field,
            ElementGroup.field(cityTop, cityBottom) { left },
            ElementGroup.field(cityTop, cityBottom) { right },
        )
        add(
            Element.City,
            cityTop,
            cityBottom,
        )
        add(
            Element.CropCircle,
            ElementGroup.Center,
        )
    },
)
