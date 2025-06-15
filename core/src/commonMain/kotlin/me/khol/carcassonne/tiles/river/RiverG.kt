package me.khol.carcassonne.tiles.river

import me.khol.carcassonne.Tile
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.River
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.elements
import me.khol.carcassonne.field
import me.khol.carcassonne.river

val RiverG = Tile(
    name = "RiverG",
    edges = Edges(top = River, right = Field, bottom = Field, left = River),
    elements = elements {
        field { topLeft + leftTop }
        field { topRight + right + bottom + leftBottom }
        river { top + left }
    },
)
