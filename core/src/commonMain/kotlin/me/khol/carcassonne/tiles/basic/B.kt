package me.khol.carcassonne.tiles.basic

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.monastery

val B = Tile(
    name = "B",
    edges = Edges(top = Field, right = Field, bottom = Field, left = Field),
    elements = elements {
        field { all }
        monastery()
    },
)
