package me.khol.carcassonne.ui.hud

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    confirmTilePlacement: (phase: Phase.PlacingTile) -> Unit,
    confirmFigurePlacement: (phase: Phase.PlacingFigure) -> Unit,
    confirmAction: (action: Phase.PlacingFigure.ConfirmationAction) -> Unit,
    undo: () -> Unit,
    onTilesLeftClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    @Composable
    fun PhaseButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit,
    ) {
        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.12f)),
            modifier = modifier,
        ) {
            CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.labelLarge) {
                content()
            }
        }
    }

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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(12.dp)
                    .width(IntrinsicSize.Min)
            ) {
                when (val phase = phase) {
                    is Phase.PlacingFigure -> {
                        if (phase.placedFigure == null) {
                            if (phase.validFigurePlacements.values.flatten().isNotEmpty()) {
                                Text(
                                    text = "Place a meeple",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .align(alignment = Alignment.CenterHorizontally)
                                )
                                Text(
                                    text = "or",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }

                            phase.confirmationActions.forEach { action ->
                                PhaseButton(
                                    onClick = { confirmAction(action) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(tileSize / 2)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = when (action) {
                                                is Phase.PlacingFigure.ConfirmationAction.ScoreAbbot -> "Score abbot"
                                            },
                                        )
                                    }
                                }
                                Text(
                                    text = "or",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        } else {
                            if (phase.validFigurePlacements.values.flatten().isNotEmpty()) {
                                Text(
                                    text = "Move a meeple",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .align(alignment = Alignment.CenterHorizontally)
                                )
                                Text(
                                    text = "or",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }

                        PhaseButton(
                            onClick = { confirmFigurePlacement(phase) },
                            modifier = Modifier
                                .size(tileSize)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                if (phase.placedFigure == null) {
                                    Text(
                                        text = "Skip",
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
                        PhaseButton(
                            onClick = onTilesLeftClick,
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
                        if (phase.placedTile == null) {
                            Tile(
                                tile = phase.tile.toUiTile(),
                            )
                        } else {
                            PhaseButton(
                                onClick = { confirmTilePlacement(phase) },
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

        if (phase is Phase.Undoable && phase.undo() != phase) {
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
        validTilePlacements = emptyMap(),
        validFigurePlacements = previewRotatedTile.rotatedElements.all().associateWith { rotatedElement ->
            listOf(
                PlacedFigure(
                    placedElement = rotatedElement.placed(1, 0),
                    figure = PlayerFigure(figure = Meeple, player = Players.green),
                )
            )
        },
        placedFigure = null,
        confirmationActions = listOf(
            Phase.PlacingFigure.ConfirmationAction.ScoreAbbot(
                figure = PlacedFigure(
                    placedElement = Tiles.Basic.A.monastery.rotated(Rotation.ROTATE_0).placed(0, 0),
                    figure = PlayerFigure(
                        figure = Abbot,
                        player = Players.green,
                    ),
                ),
            ),
        ),
    )
    private val previewPhasePlacingFigurePlaced = previewPhasePlacingFigureFresh.copy(
        placedFigure = PlacedFigure(
            placedElement = Tiles.Basic.D.road.rotated(Rotation.ROTATE_180).placed(0, 0),
            figure = PlayerFigure(
                figure = Abbot,
                player = Players.green,
            ),
        )
    )

    override fun getDisplayName(index: Int): String? = when (index) {
        0 -> "PlacingTile"
        1 -> "PlacingTile placed tile"
        2 -> "PlacingFigure"
        3 -> "PlacingFigure placed figure"
        else -> null
    }

    override val values = sequenceOf<Phase>(
        Phase.PlacingTile(tile = previewTile, validTilePlacements = emptyMap()),
        Phase.PlacingTile(tile = previewTile, validTilePlacements = emptyMap(), placedTile = previewPlacedTile),
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
                confirmAction = {},
                undo = {},
                onTilesLeftClick = {},
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
