package me.khol.carcassonne.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Board(
    tiles: List<PlacedTile>,
) {
    Box(
        Modifier.fillMaxSize()
    ) {
        val tileSize = 128.dp
        tiles.forEach { tile ->
            Tile(
                placedTile = tile,
                modifier = Modifier.offset(tileSize * tile.x, tileSize * tile.y)
            )
        }
    }
}
