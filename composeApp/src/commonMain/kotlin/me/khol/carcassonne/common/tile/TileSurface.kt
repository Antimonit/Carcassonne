package me.khol.carcassonne.common.tile

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
    val scale by animateFloatAsState(if (isHovered) 1.1f else 1f)

    Surface(
        modifier = modifier
            .size(tileSize)
            .padding(2.dp)
            .hoverable(interactionSource = interactionSource)
            .scale(scale),
        elevation = elevation,
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
