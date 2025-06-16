package me.khol.carcassonne.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
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
    modifier: Modifier = Modifier,
) {
    TileSurface(
        modifier = modifier,
    ) {
        Image(
            bitmap = imageResource(drawable),
            contentDescription = null,
            filterQuality = FilterQuality.High,
            modifier = Modifier
                .rotate(
                    degrees = when (rotation) {
                        Rotation.ROTATE_0 -> 0f
                        Rotation.ROTATE_90 -> 90f
                        Rotation.ROTATE_180 -> 180f
                        Rotation.ROTATE_270 -> 270f
                    }
                )
        )
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
