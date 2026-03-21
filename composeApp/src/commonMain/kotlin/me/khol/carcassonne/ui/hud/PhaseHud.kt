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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Phase
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.PlayerFigure
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.figure.Abbot
import me.khol.carcassonne.figure.Meeple
import me.khol.carcassonne.fixtures.Players
import me.khol.carcassonne.placed
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.ui.Tile
import me.khol.carcassonne.ui.tile.tileSize
import me.khol.carcassonne.ui.tile.toUiTile

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
                                if (phase.selectedFigure == null) {
                                    Text(
                                        text = "Skip",
                                        fontWeight = FontWeight.Bold,
                                    )
                                } else {
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
                                    tile = phase.tile.toUiTile(),
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

                    is Phase.Scoring -> {
                        Text(
                            text = "Scoring",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }

        if (phase is Phase.Undoable) {
            UndoButton(onClick = undo)
        }
    }
}

private class PhaseParameterProvider : PreviewParameterProvider<Phase> {

    private val previewTile = Tiles.Basic.D.tile
    private val previewRotatedTile = previewTile.rotated(Rotation.ROTATE_180)
    private val previewPlacedTile = previewRotatedTile.placed(1, 0)
    private val previewPhasePlacingFigureFresh = Phase.PlacingFigure(
        placedTile = previewPlacedTile,
        validFigurePlacements = previewRotatedTile.rotatedElements.all().associateWith { rotatedElement ->
            listOf(
                PlacedFigure(
                    placedElement = rotatedElement.placed(1, 0),
                    figure = PlayerFigure(figure = Meeple, player = Players.green),
                )
            )
        },
        selectedFigure = null,
    )
    private val previewPhasePlacingFigurePlaced = previewPhasePlacingFigureFresh.copy(
        selectedFigure = PlacedFigure(
            placedElement = Tiles.Basic.D.road.rotated(Rotation.ROTATE_180).placed(0, 0),
            figure = PlayerFigure(
                figure = Abbot,
                player = Players.green,
            ),
        )
    )

    override fun getDisplayName(index: Int): String? = when (index) {
        0 -> "PlacingTile.Fresh"
        1 -> "PlacingTile.Placed"
        2 -> "PlacingFigure"
        3 -> "PlacingFigure placed figure"
        else -> null
    }

    override val values = sequenceOf(
        Phase.PlacingTile.Fresh(tile = previewTile),
        Phase.PlacingTile.Placed(placedTile = previewPlacedTile),
        previewPhasePlacingFigureFresh,
        previewPhasePlacingFigurePlaced,
    )
}

@Preview
@Composable
private fun PhaseHudPreview(
    @PreviewParameter(PhaseParameterProvider::class)
    phase: Phase,
) {
    MaterialTheme {
        Surface {
            PhaseHud(
                phase = phase,
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
