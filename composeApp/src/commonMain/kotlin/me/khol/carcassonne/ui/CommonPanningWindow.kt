package me.khol.carcassonne.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateBy
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.launch

@Composable
fun CommonPanningWindow(
    contents: @Composable () -> Unit,
) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    // Zoom and rotation are relative to screen center rather than to gesture centroid.
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .clickable(enabled = !state.isTransformInProgress) {
                scope.launch {
                    // Zoom onto the starting tile
                    state.animateBy(
                        zoomFactor = 1 / scale * 2,
                        panOffset = -offset,
                        rotationDegrees = -rotation,
                    )
                }
            }
            .fillMaxSize()
            .transformable(state = state)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                rotationZ = rotation
                translationX = offset.x
                translationY = offset.y
            },
        contentAlignment = Alignment.Center,
    ) {
        contents()
    }
}
