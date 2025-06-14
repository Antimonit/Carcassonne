package me.khol.carcassonne.ui

import androidx.compose.runtime.Composable

@Composable
actual fun PanningWindow(contents: @Composable (() -> Unit)) {
    contents()
}
