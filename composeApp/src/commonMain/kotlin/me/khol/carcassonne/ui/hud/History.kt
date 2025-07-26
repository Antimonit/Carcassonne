package me.khol.carcassonne.ui.hud

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider
import kotlinx.coroutines.launch
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.History
import me.khol.carcassonne.PlacedTile
import me.khol.carcassonne.Player
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.ui.SimpleBoard
import me.khol.carcassonne.ui.Tile
import me.khol.carcassonne.ui.TileFiguresOverlay
import me.khol.carcassonne.ui.tile.toUiTile
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun History(
    history: History,
    modifier: Modifier = Modifier,
) {
    if (history.events.isNotEmpty()) {
        Box(
            modifier = modifier
        ) {
            Surface(
                shadowElevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.12f)),
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = 0.5f
                        scaleY = 0.5f
                        transformOrigin = TransformOrigin(1f, 0.5f)
                    }
                    .height(480.dp)
            ) {
                val state = rememberLazyListState()
                LazyColumn(
                    state = state,
                    verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Top),
                    modifier = Modifier
                        .padding(12.dp)
                ) {
                    val items = history.events.reversed()
                    items(
                        count = items.size,
                        key = { index -> items.size - index },
                        contentType = { index -> items[index]::class }
                    ) { index ->
                        when (val event = items[index]) {
                            is History.Event.TilePlacement -> {
                                val tooltipState = rememberTooltipState(
                                    isPersistent = true,
                                )
                                val scope = rememberCoroutineScope()

                                HistoryEventPopup(
                                    event = event,
                                    state = tooltipState,
                                ) {
                                    Surface(
                                        onClick = {
                                            scope.launch {
                                                tooltipState.show(MutatePriority.UserInput)
                                            }
                                        },
                                        color = event.player.color.uiColor(),
                                        shape = RoundedCornerShape(4.dp),
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(2.dp)
                                        ) {
                                            Spacer(modifier = Modifier.width(16.dp))
                                            val uiTile = event.placedTile.rotatedTile.tile.toUiTile()
                                            Tile(
                                                drawable = uiTile.drawable,
                                                rotation = event.placedTile.rotatedTile.rotation,
                                                overlay = {
                                                    val placedFigure = event.placedFigure
                                                    if (placedFigure != null) {
                                                        TileFiguresOverlay(
                                                            figures = listOf(placedFigure),
                                                            uiTile = uiTile,
                                                            rotation = event.placedTile.rotatedTile.rotation,
                                                        )
                                                    }
                                                },
                                                modifier = Modifier
                                                    .animateItem()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                LaunchedEffect(history.events) {
                    state.animateScrollToItem(0)
                }
            }
        }
    }
}

private fun Player.Color.uiColor(): Color = when (this) {
    Player.Color.Black -> Color(0xFF323031)
    Player.Color.Blue -> Color(0xFF1C70B1)
    Player.Color.Green -> Color(0xFF33A647)
    Player.Color.Pink -> Color(0xFFEA7FB6)
    Player.Color.Red -> Color(0xFFD71F26)
    Player.Color.Yellow -> Color(0xFFEDE035)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryEventPopup(
    event: History.Event.TilePlacement,
    state: TooltipState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val tooltipAnchorSpacing = with(LocalDensity.current) { 4.dp.roundToPx() }
    TooltipBox(
        positionProvider = remember {
            object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize,
                ) = IntOffset(
                    y = anchorBounds.top + (anchorBounds.height - popupContentSize.height) / 2,
                    x = anchorBounds.left - tooltipAnchorSpacing - popupContentSize.width,
                )
            }
        },
        tooltip = {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceContainer,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            ) {
                SimpleBoard(
                    board = event.board,
                    center = event.placedTile.coordinates,
                    modifier = Modifier
                        .size(240.dp)
                )
            }
        },
        state = state,
        modifier = modifier,
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HistoryEventPopupPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .size(400.dp)
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                val rotatedTile = RotatedTile(
                    tile = Tiles.Basic.D,
                    rotation = Rotation.ROTATE_90,
                )
                val coordinates = Coordinates(1, 0)
                HistoryEventPopup(
                    event = History.Event.TilePlacement(
                        player = Player("Red", Player.Color.Red),
                        placedTile = PlacedTile(
                            rotatedTile = rotatedTile,
                            coordinates = coordinates,
                        ),
                        placedFigure = null,
                        board = me.khol.carcassonne.Board.starting(Tiles.Basic.A)
                            .placeTile(coordinates, rotatedTile, placedFigures = emptyList()),
                    ),
                    state = rememberTooltipState(initialIsVisible = true),
                ) {
                    Text(
                        text = "Tile",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color.Red)
                    )
                }
            }
        }
    }
}
