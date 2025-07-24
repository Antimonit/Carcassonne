package me.khol.carcassonne.ui.hud

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.History
import me.khol.carcassonne.Player
import me.khol.carcassonne.ui.Tile
import me.khol.carcassonne.ui.TileFiguresOverlay
import me.khol.carcassonne.ui.tile.toUiTile

@Composable
fun History(
    history: History,
    modifier: Modifier = Modifier,
) {
    if (history.events.isNotEmpty()) {
        val interactionSource = remember { MutableInteractionSource() }
        Surface(
            shadowElevation = 4.dp,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.12f)),
            modifier = modifier
                .heightIn(max = 480.dp)
                .graphicsLayer {
                    scaleX = 0.5f
                    scaleY = 0.5f
                    transformOrigin = TransformOrigin(1f, 1f)
                }
                .hoverable(interactionSource = interactionSource)
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
                            Surface(
                                color = event.player.color.uiColor(),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier
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
            LaunchedEffect(history.events) {
                state.animateScrollToItem(0)
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
