package me.khol.carcassonne.ui.hud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Phase
import me.khol.carcassonne.PlacedTile
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.ui.Tile
import me.khol.carcassonne.ui.tile.tileSize
import me.khol.carcassonne.ui.tile.toUiTile
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PhaseHud(
    phase: Phase,
    remainingTilesCount: Int,
    confirmTilePlacement: (phase: Phase.PlacingTile.Placed) -> Unit,
    confirmFigurePlacement: (phase: Phase.PlacingFigure) -> Unit,
    undo: () -> Unit,
    onTilesLeftClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 8.dp,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(12.dp)
                    .width(IntrinsicSize.Min)
            ) {
                when (val phase = phase) {
                    is Phase.PlacingFigure -> {
                        Text(
                            text = "Place a meeple",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                        )

                        Surface(
                            onClick = { confirmFigurePlacement(phase) },
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
                        Surface(
                            onClick = onTilesLeftClick,
                            shape = RoundedCornerShape(4.dp),
                            color = Color.Transparent,
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                                .fillMaxWidth()
                                .height(40.dp)
                        ) {
                            Box {
                                Text(
                                    text = "$remainingTilesCount tiles left",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                        when (phase) {
                            is Phase.PlacingTile.Fresh -> {
                                Tile(
                                    drawable = phase.tile.toUiTile().drawable,
                                    rotation = Rotation.ROTATE_0,
                                )
                            }

                            is Phase.PlacingTile.Placed -> {
                                Surface(
                                    onClick = { confirmTilePlacement(phase) },
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

        if (phase is Phase.Undoable) {
            UndoButton(onClick = undo)
        }
    }
}


@Preview
@Composable
private fun PhaseHudPlacingTilePreview() {
    MaterialTheme {
        Surface {
            PhaseHud(
                phase = Phase.PlacingTile.Fresh(
                    tile = Tiles.Basic.D,
                ),
                remainingTilesCount = 71,
                confirmTilePlacement = {},
                confirmFigurePlacement = {},
                undo = {},
                onTilesLeftClick = {},
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
@Preview
@Composable
private fun PhaseHudPlacingFigurePreview() {
    MaterialTheme {
        Surface {
            PhaseHud(
                phase = Phase.PlacingFigure.Fresh(
                    tile = PlacedTile(
                        rotatedTile = RotatedTile(
                            tile = Tiles.Basic.D,
                            rotation = Rotation.ROTATE_180,
                        ),
                        coordinates = Coordinates(1, 0),
                    ),
                    validElements = emptySet(),
                ),
                remainingTilesCount = 71,
                confirmTilePlacement = {},
                confirmFigurePlacement = {},
                undo = {},
                onTilesLeftClick = {},
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
