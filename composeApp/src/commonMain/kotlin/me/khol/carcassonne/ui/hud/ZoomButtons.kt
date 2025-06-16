package me.khol.carcassonne.ui.hud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ZoomButtons(
    modifier: Modifier = Modifier,
    onZoomInClick: () -> Unit,
    onZoomOutClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        elevation = 1.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(16.dp)),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val useZoomIcons = false
            val zoomInIcon = if (useZoomIcons) Icons.Default.ZoomIn else Icons.Filled.Add
            val zoomOutIcon = if (useZoomIcons) Icons.Default.ZoomOut else Icons.Filled.Remove
            TextButton(
                modifier = Modifier.size(48.dp),
                onClick = onZoomInClick,
            ) {
                Icon(zoomInIcon, contentDescription = "Zoom in")
            }
            TextButton(
                modifier = Modifier.size(48.dp),
                onClick = onZoomOutClick,
            ) {
                Icon(zoomOutIcon, contentDescription = "Zoom out")
            }
        }
    }
}
