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
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.basic.D
import me.khol.carcassonne.tiles.basicTileset
import me.khol.carcassonne.ui.tile.toDrawable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    var game by remember {
        mutableStateOf(
            Game.new(
                tilesets = listOf(basicTileset),
                startingTile = D,
            )
        )
    }

    MaterialTheme {
        Surface(
            color = MaterialTheme.colors.background,
        ) {
            Box {
                PanningWindow {
                    Board(
                        board = game.board,
                        currentTile = game.currentTile,
                        onPlaceTile = { coordinates, tile ->
                            game = game.copy(
                                board = game.board.placeTile(tile.coordinates, tile.rotatedTile),
                                remainingTiles = game.remainingTiles.drop(1),
                            )
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
                        val current = game.currentTile
                        if (current == null) {
                            Text(
                                text = "No tiles left",
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterHorizontally)
                            )
                        } else {
                            Text(
                                text = "${game.remainingTiles.size} tiles left",
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
