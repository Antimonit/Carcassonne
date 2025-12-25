package me.khol.carcassonne.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.ui.tile.RotatedUiTile
import me.khol.carcassonne.ui.tile.basic.A
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RotatedTile(
    rotatedUiTile: RotatedUiTile,
    overlay: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Tile(
        tile = rotatedUiTile.uiTile,
        overlay = overlay,
        modifier = Modifier
            .rotate(degrees = shortestRotation(rotatedUiTile.rotation))
            .then(modifier)
    )
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

@Preview
@Composable
private fun RotatedTilePreview() {
    RotatedTile(
        rotatedUiTile = RotatedUiTile(
            uiTile = A,
            rotation = Rotation.ROTATE_0,
        ),
    )
}
