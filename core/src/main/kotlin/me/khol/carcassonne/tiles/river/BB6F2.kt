package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Element
import me.khol.carcassonne.Positions
import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements

val BB6F2 = Tile(
    name = "BB6F2",
    edges = Edges(top = City, right = River, bottom = Road, left = River),
    elements = elements {
        val city = Positions.city { top }
        add(
            Element.Field,
            Positions.field(city) { leftTop },
            Positions.field(city) { rightTop },
            Positions.field { rightBottom + bottomRight },
            Positions.field { bottomLeft + leftBottom },
        )
        add(
            Element.River,
            Positions.edges { left + right },
        )
        add(
            Element.Road,
            Positions.road { bottom },
        )
        add(
            Element.City,
            city,
        )
    },
)
