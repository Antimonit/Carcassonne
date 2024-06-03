package me.khol.carcassonne.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import getPlatform
import me.khol.carcassonne.common.Tile.Edge.*
import me.khol.carcassonne.common.Tile.Edges

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    val platformName = getPlatform().name

    PanningWindow {
        Board(
            listOf(
                PlacedTile(x = 1, y = 1, tile = Tile(Edges(top = River, right = River, bottom = Road, left = Road))),
                PlacedTile(x = 2, y = 1, tile = Tile(Edges(top = City, right = River, bottom = Field, left = River))),
                PlacedTile(x = 3, y = 1, tile = Tile(Edges(top = Road, right = River, bottom = Road, left = River))),
                PlacedTile(x = 3, y = 2, tile = Tile(Edges(top = Road, right = Field, bottom = Road, left = City))),
                PlacedTile(x = 4, y = 1, tile = Tile(Edges(top = Field, right = Field, bottom = River, left = River))),
                PlacedTile(x = 4, y = 2, tile = Tile(Edges(top = River, right = Field, bottom = Field, left = Field))),
                PlacedTile(x = 3, y = 0, tile = Tile(Edges(top = City, right = Road, bottom = Road, left = City))),
                PlacedTile(x = 1, y = 0, tile = Tile(Edges(top = Field, right = Field, bottom = River, left = River))),
                PlacedTile(x = 0, y = 0, tile = Tile(Edges(top = Field, right = River, bottom = Field, left = Field))),
            )
        )
    }
}





