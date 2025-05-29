package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.monastery
import me.khol.carcassonne.road

val A = Tile(
    name = "A",
    edges = Edges(top = Field, right = Field, bottom = Road, left = Field),
    elements = elements {
        field { all }
        road { bottom }
        monastery()
    },
)
