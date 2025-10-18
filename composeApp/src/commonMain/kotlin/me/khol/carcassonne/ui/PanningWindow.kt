package me.khol.carcassonne.ui

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
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
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.toOffset

private fun Float.coerceZoom(): Float = coerceIn(0.4f, 2f)

/**
 * A pan and zoom container.
 *
 * Supports panning via mouse click and drag or via touch and drag, and
 * zooming via mouse wheel or by pinching gesture.
 */
@Composable
fun PanningWindow(
    contents: @Composable () -> Unit,
) {
    var pan by remember { mutableStateOf(Offset.Zero) }
    var zoom by remember { mutableFloatStateOf(1f) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            // Handle mouse scroll zoom
            .pointerInput(Unit) {
                awaitEachGesture {
                    val event = awaitPointerEvent()
                    if (event.type == PointerEventType.Scroll) {
                        event.changes.forEach { change ->
                            val scrollDelta = change.scrollDelta.y
                            val gestureZoom = 1f - scrollDelta / 50f

                            val oldScale = zoom
                            val newScale = (zoom * gestureZoom).coerceZoom()

                            // Zoom toward pointer position
                            val pointerOffset = change.position - size.center.toOffset()
                            pan += (pointerOffset / oldScale) - (pointerOffset / newScale)
                            zoom = newScale
                        }
                    }
                }
            }
            // Handle touch/drag gestures
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown(requireUnconsumed = false)

                    do {
                        val event = awaitPointerEvent()
                        val canceled = event.changes.any { it.isConsumed }

                        if (!canceled) {
                            val currentZoom = event.calculateZoom()
                            val currentCentroid = event.calculateCentroid(useCurrent = true)
                            val currentPan = event.calculatePan()

                            if (currentZoom != 1f || currentPan != Offset.Zero) {
                                val oldScale = zoom
                                val newScale = (zoom * currentZoom).coerceZoom()

                                // Zoom toward centroid, then apply pan
                                val centroidOffset = currentCentroid - size.center.toOffset()
                                pan += (centroidOffset / oldScale) - (centroidOffset / newScale) - (currentPan / oldScale)
                                zoom = newScale

                                event.changes.forEach { it.consume() }
                            }
                        }
                    } while (!canceled && event.changes.any { it.pressed })
                }
            }
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
