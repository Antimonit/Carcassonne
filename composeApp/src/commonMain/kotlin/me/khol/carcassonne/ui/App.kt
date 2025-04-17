package me.khol.carcassonne.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Board
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.basic.D
import me.khol.carcassonne.tiles.basicTileset
import me.khol.carcassonne.ui.tile.toDrawable
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

val random = Random(seed = 42)
val tiles = basicTileset.tileCounts.flatMap { tileCount ->
    List(tileCount.count) { tileCount.tile }
}.shuffled(random)

@Preview
@Composable
fun App() {
    var board by remember { mutableStateOf(Board.starting(startingTile = D)) }
    var remainingTiles by remember { mutableStateOf(tiles) }
    var currentTile by remember { mutableStateOf(remainingTiles.firstOrNull()) }

    MaterialTheme {
        Surface(
            color = MaterialTheme.colors.background,
        ) {
            Box {
                PanningWindow {
                    Board(
                        board = board,
                        currentTile = currentTile,
                        onPlaceTile = { coordinates, tile ->
                            board = board.placeTile(tile.coordinates, tile.rotatedTile)
                            remainingTiles = remainingTiles.drop(1)
                            currentTile = remainingTiles.firstOrNull()
                        }
                    )
                }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(alignment = Alignment.BottomStart)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(12.dp)
                    ) {
                        val current = currentTile
                        if (current == null) {
                            Text(
                                text = "No tiles left",
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterHorizontally)
                            )
                        } else {
                            Text(
                                text = "${remainingTiles.size} tiles left",
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterHorizontally)
                            )
                            Tile(
                                drawable = current.toDrawable(),
                                rotation = Rotation.ROTATE_0,
                            )
                        }
                    }
                }
            }
        }
    }
}
