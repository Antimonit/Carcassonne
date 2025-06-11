package me.khol.carcassonne.common

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.toOffset

@Composable
actual fun PanningWindow(
    contents: @Composable (() -> Unit),
) {
    var pan by remember { mutableStateOf(Offset.Zero) }
    var zoom by remember { mutableFloatStateOf(1f) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .pointerInput(Unit) {
                detectTransformGestures(
                    onGesture = { centroid, gesturePan, gestureZoom, _ ->
                        val oldScale = zoom
                        val newScale = zoom * gestureZoom

                        pan =
                            (pan + (centroid - size.center.toOffset()) / oldScale) -
                                ((centroid - size.center.toOffset()) / newScale + gesturePan / oldScale)
                        zoom = newScale
                    }
                )
            }
            .graphicsLayer {
                translationX = -pan.x * zoom
                translationY = -pan.y * zoom
                scaleX = zoom
                scaleY = zoom
                transformOrigin = TransformOrigin(0.5f, 0.5f)
            }
            .fillMaxSize()
    ) {
        contents()
    }
}
