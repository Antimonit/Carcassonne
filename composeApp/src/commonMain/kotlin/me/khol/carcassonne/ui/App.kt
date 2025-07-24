package me.khol.carcassonne.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Engine
import me.khol.carcassonne.Game
import me.khol.carcassonne.Phase
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basicTileset
import me.khol.carcassonne.ui.hud.History
import me.khol.carcassonne.ui.hud.UndoButton
import me.khol.carcassonne.ui.tile.tileSize
import me.khol.carcassonne.ui.tile.toUiTile
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    val engine = remember {
        Engine(
            initialGame = Game.new(
                tilesets = listOf(basicTileset),
                startingTile = Tiles.Basic.D,
            )
        )
    }
    val game by engine.game.collectAsState()

    GameSurface {
        PanningWindow {
            Board(
                board = game.board,
                phase = game.phase,
                onPlaceTile = engine::placeTile,
                onPlaceFigure = engine::placeFigure,
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.BottomStart)
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 8.dp,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(12.dp)
                ) {
                    when (val phase = game.phase) {
                        is Phase.PlacingFigure -> {
                            Text(
                                text = "Place a meeple",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterHorizontally)
                            )

                            Surface(
                                onClick = { engine.confirmFigurePlacement(phase) },
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier
                                    .size(tileSize)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    when (phase) {
                                        is Phase.PlacingFigure.Fresh -> {
                                            Text(
                                                text = "Skip",
                                                fontWeight = FontWeight.Bold,
                                            )
                                        }
                                        is Phase.PlacingFigure.Placed -> {
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
                            }
                        }
                        is Phase.PlacingTile -> {
                            Text(
                                text = "${game.remainingTiles.size} tiles left",
                                style = MaterialTheme.typography.bodyMedium,
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
                                    Surface(
                                        onClick = { engine.confirmTilePlacement(phase) },
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
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterHorizontally)
                            )
                        }
                        Phase.Scoring -> Unit
                    }
                }
            }

            if (game.phase is Phase.Undoable) {
                UndoButton(onClick = engine::undo)
            }
        }

        History(
            history = game.history,
            modifier = Modifier
                .padding(8.dp)
                .align(alignment = Alignment.BottomEnd)
        )
    }
}
