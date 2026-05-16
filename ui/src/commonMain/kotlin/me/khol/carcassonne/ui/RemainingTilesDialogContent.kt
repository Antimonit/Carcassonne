package me.khol.carcassonne.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Tile
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basicTileset
import me.khol.carcassonne.ui.tile.toUiTile
import kotlin.random.Random

@Composable
fun RemainingTilesDialogContent(
    allTiles: List<Tile>,
    remainingTiles: List<Tile>,
) {
    val scrollState = rememberScrollState()
    val elevation by animateDpAsState(
        if (scrollState.canScrollBackward) 4.dp else 0.dp
    )
    val headerSize = 64.dp

    DialogWithHeader(
        modifier = Modifier.padding(vertical = 48.dp),
        header = {
            Surface(
                tonalElevation = elevation,
                shadowElevation = elevation,
                modifier = Modifier.size(headerSize)
            ) {
                Box {
                    Text(
                        text = "Remaining tiles",
                        style = MaterialTheme.typography.titleLarge,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterStart)
                    )
                }
            }
        },
        content = {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(top = headerSize)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            ) {
                val remainingMap = remainingTiles
                    .groupingBy { it }
                    .eachCount()

                allTiles
                    .groupingBy { it }
                    .eachCount()
                    .map { (key, allCount) ->
                        Usage(
                            tile = key,
                            total = allCount,
                            remaining = remainingMap.getOrElse(key) { 0 }
                        )
                    }
                    .sortedBy { it.tile.name }
                    .forEach { usage ->
                        RemainingTile(
                            usage = usage,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
            }
        },
    )
}

@Composable
private fun DialogWithHeader(
    header: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
) {
    Surface(
        shape = shape,
        modifier = modifier,
    ) {
        Layout(
            content = {
                header()
                content()
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

@Composable
private fun RemainingTile(
    usage: Usage,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        val filter = if (usage.remaining == 0) {
            ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
        } else {
            null
        }

        Tile(
            tile = usage.tile.toUiTile(),
            modifier = Modifier
                .size(112.dp)
                .graphicsLayer {
                    colorFilter = filter
                }
        )

        DotsRow(
            total = usage.total,
            remaining = usage.remaining,
        )
    }
}

private data class Usage(
    val tile: Tile,
    val total: Int,
    val remaining: Int,
)

@Composable
private fun DotsRow(
    total: Int,
    remaining: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier,
    ) {
        repeat(total) {
            val background = if (it < remaining) LocalContentColor.current else Color.Transparent
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(background, CircleShape)
                    .border(1.dp, LocalContentColor.current, CircleShape)
            )
        }
    }
}

@Preview
@Composable
private fun RemainingTilesDialogPreview() {
    MaterialTheme {
        val allTiles = basicTileset.tileCounts.flatMap { tileCount ->
            List(tileCount.count) {
                tileCount.tile
            }
        }
        val remainingTiles = allTiles
            .shuffled(Random(1))
            .take(allTiles.size / 2)

        RemainingTilesDialogContent(
            allTiles = allTiles,
            remainingTiles = remainingTiles
        )
    }
}

@Composable
private fun BaseRemainingTilePreview(
    remaining: Int,
) {
    MaterialTheme {
        Surface {
            RemainingTile(
                usage = Usage(
                    tile = Tiles.Basic.D.tile,
                    total = 4,
                    remaining = remaining,
                ),
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun RemainingTilePreview() {
    BaseRemainingTilePreview(remaining = 3)
}

@Preview
@Composable
private fun RemainingTileAllUsedPreview() {
    BaseRemainingTilePreview(remaining = 0)
}

@Preview
@Composable
private fun DotsRowPreview() {
    MaterialTheme {
        Surface {
            DotsRow(total = 4, remaining = 1, modifier = Modifier.padding(4.dp))
        }
    }
}
