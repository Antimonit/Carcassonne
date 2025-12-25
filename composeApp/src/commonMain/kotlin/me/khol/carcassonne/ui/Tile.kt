package me.khol.carcassonne.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import me.khol.carcassonne.ui.tile.TileSurface
import me.khol.carcassonne.ui.tile.basic.A
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Tile(
    drawable: DrawableResource,
    overlay: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    TileSurface(
        modifier = modifier
    ) {
        Image(
            bitmap = imageResource(drawable),
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
        drawable = A.drawable,
    )
}
