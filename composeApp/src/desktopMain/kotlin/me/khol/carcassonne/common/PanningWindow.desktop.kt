package me.khol.carcassonne.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.toOffset
import kotlin.math.PI
import kotlin.math.atan2

@Composable
actual fun PanningWindow(
    contents: @Composable (() -> Unit),
) {
    var zoom by remember { mutableStateOf(1f) }
    var pan by remember { mutableStateOf(Offset.Zero) }
    var rotate by remember { mutableFloatStateOf(0f) }

    val mouseRotateModifier = Modifier
        .pointerInput(Unit) {
            fun Offset.toAngle(): Float = atan2(x, y) / PI.toFloat() / 2 * 360

            var previousOffset: Offset = Offset.Unspecified
            var angle = 0f

            @OptIn(ExperimentalFoundationApi::class)
            detectDragGestures(
                matcher = PointerMatcher.mouse(PointerButton.Tertiary),
                onDragStart = {
                    previousOffset = it
                    val centroid = size.center.toOffset()
                    angle = (centroid - previousOffset).toAngle()
                },
                onDrag = { dragAmount: Offset ->
                    previousOffset += dragAmount
                    val centroid = size.center.toOffset()
                    val polarAngle = (centroid - previousOffset).toAngle()
                    val gestureRotate = (angle - polarAngle)
                    angle = polarAngle
                    pan = pan.rotateBy(gestureRotate)
                    rotate += gestureRotate
                }
            )
        }

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
            .then(mouseRotateModifier)
            .then(mouseZoomModifier)
            .then(mousePanModifier)
            .graphicsLayer {
                translationX = -pan.x * zoom
                translationY = -pan.y * zoom
                scaleX = zoom
                scaleY = zoom
                rotationZ = rotate
                transformOrigin = TransformOrigin(0.5f, 0.5f)
            }
    ) {
        contents()
    }
}
