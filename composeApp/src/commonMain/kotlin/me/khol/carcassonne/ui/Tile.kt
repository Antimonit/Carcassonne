package me.khol.carcassonne.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.FilterQuality
import carcassonne.composeapp.generated.resources.*
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.ui.tile.TileSurface
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Tile(
    drawable: DrawableResource,
    rotation: Rotation,
    overlay: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    TileSurface(
        modifier = Modifier
            .rotate(degrees = shortestRotation(rotation))
            .then(modifier)
    ) {
        Image(
            bitmap = imageResource(drawable),
            contentDescription = null,
            filterQuality = FilterQuality.High,
        )

        overlay()
    }
}

@Composable
private fun shortestRotation(rotation: Rotation): Float {
    val target = when (rotation) {
        Rotation.ROTATE_0 -> 0f
        Rotation.ROTATE_90 -> 90f
        Rotation.ROTATE_180 -> 180f
        Rotation.ROTATE_270 -> 270f
    }
    val degrees = remember { Animatable(target) }

    LaunchedEffect(target) {
        degrees.animateTo(
            targetValue = shortestAngle(current = degrees.value, target = target),
        )
        degrees.snapTo(degrees.value % 360)
    }

    return degrees.value
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
        drawable = Res.drawable.tile_basic_A,
        rotation = Rotation.ROTATE_0,
    )
}
