package me.khol.carcassonne.ui

import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.round

@Composable
actual fun PanningWindow(contents: @Composable (() -> Unit)) {
    var offset by remember { mutableStateOf(Offset.Zero) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .draggable2D(
                state = rememberDraggable2DState { delta ->
                    offset += delta
                }
            )
            .offset { offset.round() }
    ) {
        contents()
    }
}
