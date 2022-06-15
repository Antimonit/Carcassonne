package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementGroup
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
        val city = ElementGroup.city { top }
        add(
            Element.Field,
            ElementGroup.field(city) { left + right + bottom },
        )
        add(
            Element.City,
            city,
        )
        add(
            Element.Road,
            ElementGroup.road { bottom },
        )
        add(
            Element.CropCircle,
            ElementGroup.Center,
        )
    },
)
