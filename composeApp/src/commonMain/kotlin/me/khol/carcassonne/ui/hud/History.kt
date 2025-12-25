package me.khol.carcassonne.ui.hud

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.khol.carcassonne.History
import me.khol.carcassonne.Player
import me.khol.carcassonne.ui.RotatedTile
import me.khol.carcassonne.ui.TileFiguresOverlay
import me.khol.carcassonne.ui.tile.tileSize
import me.khol.carcassonne.ui.tile.toUiTile

@Composable
fun History(
    history: History,
    onSelectedEventChange: (History.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (history.events.isNotEmpty()) {
        Surface(
            shadowElevation = 4.dp,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.12f)),
            modifier = modifier
                .graphicsLayer {
                    scaleX = 0.5f
                    scaleY = 0.5f
                    transformOrigin = TransformOrigin(1f, 0.5f)
                }
        ) {
            Box {
                val state = rememberPagerState(initialPage = history.events.indices.last) { history.events.size }
                val scope = rememberCoroutineScope()
                val density = LocalDensity.current

                LaunchedEffect(Unit) {
                    snapshotFlow {
                        state.currentPage
                    }.collect {
                        onSelectedEventChange(history.events[it])
                    }
                }

                val itemHeight = tileSize + 4.dp // for the border
                val itemWidth = tileSize + 4.dp + 16.dp
                val initialMaxHeightPx = with(density) { 1000.dp.roundToPx() }
                var pagerHeight by remember { mutableStateOf(initialMaxHeightPx) }

                val padding = 8.dp

                val contentPadding = remember(density, pagerHeight, itemHeight) {
                    PaddingValues(
                        vertical = with(density) {
                            ((pagerHeight.toDp() - itemHeight) / 2f - padding).coerceAtLeast(0.dp)
                        }
                    )
                }

                VerticalPager(
                    state = state,
                    pageSpacing = 8.dp,
                    pageSize = PageSize.Fixed(itemHeight),
                    snapPosition = SnapPosition.Center,
                    flingBehavior = PagerDefaults.flingBehavior(
                        state = state,
                        pagerSnapDistance = PagerSnapDistance.atMost(Int.MAX_VALUE),
                    ),
                    reverseLayout = true,
                    contentPadding = contentPadding,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(itemWidth + padding * 2)
                        .onSizeChanged { size ->
                            pagerHeight = size.height
                        }
                        .padding(padding)
                ) { page: Int ->
                    val isTargetPage = (page == state.targetPage)

                    val scale by animateFloatAsState(
                        targetValue = if (isTargetPage) 1.0f else 0.9f,
                        label = "event scale"
                    )

                    // Adding an `onClick` parameter to Surface causes a side effect where
                    // the implicit `BringIntoViewRequester` inside of ViewPager will attempt
                    // to bring the item into view even when simply touched, but not pressed.
                    // Handle the clicks using low-level `pointerInput` instead.
                    Event(
                        event = history.events[page],
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            }
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        val success = tryAwaitRelease()
                                        if (success) {
                                            scope.launch {
                                                state.animateScrollToPage(page)
                                            }
                                        }
                                    }
                                )
                            }
                    )
                }

                LaunchedEffect(Unit) {
                    state.scrollToPage(history.events.indices.last)
                }
            }
        }
    }
}

@Composable
fun Event(
    event: History.Event,
    modifier: Modifier = Modifier,
) {
    when (event) {
        is History.Event.TilePlacement -> {
            TilePlacementEvent(
                event = event,
                modifier = modifier,
            )
        }
        is History.Event.Scoring -> {
            ScoringEvent(
                event = event,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun TilePlacementEvent(
    event: History.Event.TilePlacement,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = event.player.color.uiColor(),
        shape = RoundedCornerShape(4.dp),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(2.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            val rotatedUiTile = event.placedTile.rotatedTile.toUiTile()
            RotatedTile(
                rotatedUiTile = rotatedUiTile,
                overlay = {
                    val placedFigure = event.placedFigure
                    if (placedFigure != null) {
                        TileFiguresOverlay(
                            figures = listOf(placedFigure),
                            rotatedUiTile = rotatedUiTile,
                        )
                    }
                },
            )
        }
    }
}

@Composable
fun ScoringEvent(
    event: History.Event.Scoring,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = event.triggerPlayer.color.uiColor(),
        contentColor = event.triggerPlayer.color.uiOnColor(),
        shape = RoundedCornerShape(4.dp),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.padding(2.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = event.scoringPlayers.joinToString(", ") { it.name })
                Text(text = "${event.points}")
            }
        }
    }
}

fun Player.Color.uiColor(): Color = when (this) {
    Player.Color.Black -> Color(0xFF323031)
    Player.Color.Blue -> Color(0xFF1C70B1)
    Player.Color.Green -> Color(0xFF33A647)
    Player.Color.Pink -> Color(0xFFEA7FB6)
    Player.Color.Red -> Color(0xFFD71F26)
    Player.Color.Yellow -> Color(0xFFEDE035)
}

fun Player.Color.uiOnColor(): Color = when (this) {
    Player.Color.Black -> Color(0xFFFFFFFF)
    Player.Color.Blue -> Color(0xFFFFFFFF)
    Player.Color.Green -> Color(0xFFFFFFFF7)
    Player.Color.Pink -> Color(0xFF000000)
    Player.Color.Red -> Color(0xFFFFFFFF)
    Player.Color.Yellow -> Color(0xFF000000)
}
