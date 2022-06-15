package me.khol.carcassonne.tiles.crops

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
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
        val city = Positions.city { top }
        add(
            Element.Field,
            Positions.field(city) { leftTop + rightTop },
            Positions.field { leftBottom + rightBottom + bottom },
        )
        add(
            Element.City,
            city,
        )
        add(
            Element.Road,
            Positions.road { left },
            Positions.road { right },
        )
        add(
            Element.CropCircle,
            Positions.Center,
        )
    },
)
