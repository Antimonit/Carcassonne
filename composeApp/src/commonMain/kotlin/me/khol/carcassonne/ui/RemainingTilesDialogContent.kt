package me.khol.carcassonne.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.Tile
import me.khol.carcassonne.ui.tile.toUiTile

@Composable
fun RemainingTilesDialogContent(
    tiles: List<Tile>,
) {
    val scrollState = rememberScrollState()
    val elevation by animateDpAsState(
        if (scrollState.canScrollBackward) 4.dp else 0.dp
    )

    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(vertical = 48.dp)
    ) {
        Layout(
            content = {
                val headerSize = 64.dp
                Surface(
                    tonalElevation = elevation,
                    shadowElevation = elevation,
                    modifier = Modifier.size(headerSize)
                ) {
                    Text(
                        text = "Remaining tiles",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .padding(top = headerSize)
                        .verticalScroll(scrollState)
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                ) {
                    tiles
                        .sortedBy { it.name }
                        .groupingBy { it }
                        .eachCount()
                        .forEach { (tile, count) ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Tile(
                                    drawable = tile.toUiTile().drawable,
                                    rotation = Rotation.ROTATE_0,
                                    modifier = Modifier.size(80.dp)
                                )

                                Text("$count")
                            }
                        }
                }
            }
        ) { measurables, constraints ->
            val contentPlaceable = measurables[1].measure(constraints)
            val width = contentPlaceable.width

            val titlePlaceable = measurables[0].measure(
                constraints.copy(
                    minWidth = width,
                    maxWidth = width
                )
            )

            layout(contentPlaceable.width, contentPlaceable.height) {
                titlePlaceable.placeRelative(0, 0)
                contentPlaceable.placeRelative(0, 0)
            }
        }
    }
}