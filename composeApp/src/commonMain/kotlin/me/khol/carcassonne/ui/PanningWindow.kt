package me.khol.carcassonne.ui

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.toOffset
import kotlinx.coroutines.launch

fun Float.coerceZoom(): Float = coerceIn(0.4f, 2f)

/**
 * A pan and zoom container.
 *
 * Supports panning via mouse click and drag or via touch and drag, and
 * zooming via mouse wheel or by pinching gesture.
 */
@Composable
fun PanningWindow(
    state: PanZoomState,
    modifier: Modifier = Modifier,
    contents: @Composable () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            // Handle mouse scroll zoom
            .pointerInput(Unit) {
                awaitEachGesture {
                    val event = awaitPointerEvent()
                    if (event.type == PointerEventType.Scroll) {
                        event.changes.forEach { change ->
                            val scrollDelta = change.scrollDelta.y
                            val gestureZoom = 1f - scrollDelta / 50f

                            val oldScale = state.zoom
                            val newScale = (state.zoom * gestureZoom).coerceZoom()

                            // Zoom toward pointer position
                            val pointerOffset = change.position - size.center.toOffset()
                            val newPan = state.pan + (pointerOffset / oldScale) - (pointerOffset / newScale)

                            coroutineScope.launch {
                                state.panAnimatable.snapTo(newPan)
                                state.zoomAnimatable.snapTo(newScale)
                            }
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
                                val oldScale = state.zoom
                                val newScale = (state.zoom * currentZoom).coerceZoom()

                                // Zoom toward centroid, then apply pan
                                val centroidOffset = currentCentroid - size.center.toOffset()
                                val newPan = state.pan + (centroidOffset / oldScale) - (centroidOffset / newScale) - (currentPan / oldScale)

                                coroutineScope.launch {
                                    state.panAnimatable.snapTo(newPan)
                                    state.zoomAnimatable.snapTo(newScale)
                                }

                                event.changes.forEach { it.consume() }
                            }
                        }
                    } while (!canceled && event.changes.any { it.pressed })
                }
            }
            .graphicsLayer {
                translationX = -state.pan.x * state.zoom
                translationY = -state.pan.y * state.zoom
                scaleX = state.zoom
                scaleY = state.zoom
                transformOrigin = TransformOrigin(0.5f, 0.5f)
            }
    ) {
        contents()
    }
}
