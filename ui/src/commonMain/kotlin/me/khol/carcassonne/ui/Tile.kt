package me.khol.carcassonne.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.tooling.preview.Preview
import me.khol.carcassonne.ui.tile.TileSurface
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.basic.A
import org.jetbrains.compose.resources.imageResource

@Composable
fun Tile(
    tile: UiTile,
    overlay: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    TileSurface(
        modifier = modifier
    ) {
        Image(
            bitmap = imageResource(tile.drawable),
            contentDescription = null,
            filterQuality = FilterQuality.High,
        )

        overlay()
    }
}

fun shortestAngle(current: Float, target: Float): Float {
    val delta = (target - current + 360) % 360
    return when {
        delta <= 180f -> current + delta
        else -> current + delta - 360
    }
}

@Preview
@Composable
private fun TilePreview() {
    Tile(
        tile = A,
    )
}
