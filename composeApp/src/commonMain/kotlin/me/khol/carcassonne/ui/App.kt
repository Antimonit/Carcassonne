package me.khol.carcassonne.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Game
import me.khol.carcassonne.Phase
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.ui.tile.tileSize
import me.khol.carcassonne.tiles.basicTileset
import me.khol.carcassonne.ui.tile.toUiTile
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    var game by remember {
        mutableStateOf(
            Game.new(
                tilesets = listOf(basicTileset),
                startingTile = Tiles.Basic.D,
            )
        )
    }

    GameSurface {
        PanningWindow {
            Board(
                board = game.board,
                phase = game.phase,
                onPlaceTile = { tile ->
                    game = game.copy(phase = Phase.PlacingTile.Placed(placedTile = tile))
                },
                onPlaceFigure = { tile, element ->
                    game = game.copy(phase = Phase.PlacingFigure.Placed(tile, element))
                },
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
                when (val phase = game.phase) {
                    is Phase.PlacingFigure -> {
                        val onClick = {
                            val placing = phase.tile
                            game = game.copy(
                                board = game.board.placeTile(
                                    placing.coordinates,
                                    placing.rotatedTile
                                ),
                                remainingTiles = game.remainingTiles.drop(1),
                                phase = game.currentTile
                                    ?.let(Phase.PlacingTile::Fresh)
                                    ?: Phase.FinalScoring
                            )
                        }

                        Text(
                            text = "Place a meeple",
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                        )

                        @OptIn(ExperimentalMaterialApi::class)
                        Surface(
                            onClick = onClick,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .size(tileSize)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Confirm meeple placement",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(48.dp)
                                )
                            }
                        }
                    }
                    is Phase.PlacingTile -> {
                        Text(
                            text = "${game.remainingTiles.size} tiles left",
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                        )
                        when (phase) {
                            is Phase.PlacingTile.Fresh -> {
                                Tile(
                                    drawable = phase.tile.toUiTile().drawable,
                                    rotation = Rotation.ROTATE_0,
                                )
                            }
                            is Phase.PlacingTile.Placed -> {
                                val placing = phase.placedTile
                                val onClick = {
                                    game = game.copy(phase = Phase.PlacingFigure.Fresh(placing))
                                }
                                @OptIn(ExperimentalMaterialApi::class)
                                Surface(
                                    onClick = onClick,
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier
                                        .size(tileSize)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Confirm tile placement",
                                            tint = Color.Black,
                                            modifier = Modifier
                                                .size(48.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Phase.FinalScoring -> {
                        Text(
                            text = "No tiles left",
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                        )
                    }
                    Phase.Scoring -> Unit
                }
            }
        }
    }
}
