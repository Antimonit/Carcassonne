package me.khol.carcassonne.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.toOffset

@Composable
actual fun PanningWindow(
    contents: @Composable (() -> Unit),
) {
    var zoom by remember { mutableStateOf(1f) }
    var pan by remember { mutableStateOf(Offset.Zero) }

    val mousePanModifier = Modifier
        .pointerInput(Unit) {
            @OptIn(ExperimentalFoundationApi::class)
            detectDragGestures { offset ->
                pan -= offset / zoom
            }
        }

    @OptIn(ExperimentalComposeUiApi::class)
    val mouseZoomModifier = Modifier
        .onPointerEvent(PointerEventType.Scroll) {
            it.changes.forEach { change ->
                val gestureZoom = 1 - change.scrollDelta.y / 5

                val oldScale = zoom
                val newScale = zoom * gestureZoom

                val centroidOffset = change.position - size.center.toOffset()
                pan += (centroidOffset / oldScale) - (centroidOffset / newScale)
                zoom = newScale
            }
        }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .then(mouseZoomModifier)
            .then(mousePanModifier)
            .graphicsLayer {
                translationX = -pan.x * zoom
                translationY = -pan.y * zoom
                scaleX = zoom
                scaleY = zoom
                transformOrigin = TransformOrigin(0.5f, 0.5f)
            }
    ) {
        contents()
    }
}
