package me.khol.carcassonne.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.common.hud.PanButtons
import me.khol.carcassonne.common.hud.ZoomButtons

@Composable
fun PanningWindow(
    contents: @Composable () -> Unit,
) {
    // set up all transformation states
    var rawScale by remember { mutableStateOf(1f) }
    val scale by animateFloatAsState(rawScale)
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
//    val offset by animateOffsetAsState(rawOffset)
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        rawScale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
        println("scale: $scale | rotation: $rotation | offset: $offset")
    }
    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RectangleShape)
                // only for multitouch input
                .transformable(state = state)
                // for desktop single-click input
                .pointerInput(Unit) {
                    detectTransformGestures { centroid: Offset, panDelta: Offset, zoomDelta: Float, rotationDelta: Float ->
                        offset = (offset + panDelta)
                        rawScale = (scale * zoomDelta).coerceIn(0.25f, 4f)
                        rotation = (rotation + rotationDelta)
                    }
                }
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y,
                ),
            contentAlignment = Alignment.Center,
        ) {
            contents()
        }

        ZoomButtons(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterEnd),
            onZoomInClick = { rawScale *= 1.5f },
            onZoomOutClick = { rawScale /= 1.5f },
        )
        val cardSizePx = with(LocalDensity.current) { 128.dp.toPx() }

        PanButtons(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomEnd),
            onPanLeftClick = { offset += Offset(x = cardSizePx, y = 0f) },
            onPanRightClick = { offset += Offset(x = -cardSizePx, y = 0f) },
            onPanUpClick = { offset += Offset(x = 0f, y = cardSizePx) },
            onPanDownClick = { offset += Offset(x = 0f, y = -cardSizePx) },
        )
    }
}
