package me.khol.carcassonne.ui.tile

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.ui.zIndexOnHover
import org.jetbrains.compose.ui.tooling.preview.Preview

val tileSize = 128.dp

@Composable
fun TileSurface(
    modifier: Modifier = Modifier,
    contents: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val elevation by animateDpAsState(if (isHovered) 4.dp else 1.dp)

    Surface(
        modifier = modifier
            .zIndexOnHover()
            .size(tileSize)
            .aspectRatio(1f)
            .hoverable(interactionSource = interactionSource),
        shadowElevation = elevation,
        shape = RoundedCornerShape(4.dp),
    ) {
        contents()
    }
}

@Preview
@Composable
private fun TileSurfacePreview() {
    MaterialTheme {
        Surface {
            TileSurface {
                Box(
                    modifier = Modifier.background(color = Color.Gray)
                )
            }
        }
    }
}
