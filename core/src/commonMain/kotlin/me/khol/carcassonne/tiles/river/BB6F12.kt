package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.river
import me.khol.carcassonne.riverEnd

val BB6F12 = Tile(
    name = "BB6F12",
    edges = Edges(top = Field, right = Field, bottom = River, left = Field),
    elements = elements {
        field { all }
        river { bottom }
        riverEnd()
    },
)
