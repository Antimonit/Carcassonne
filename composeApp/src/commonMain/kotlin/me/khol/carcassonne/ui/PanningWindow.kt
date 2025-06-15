package me.khol.carcassonne.ui

import androidx.compose.runtime.Composable

@Composable
expect fun PanningWindow(
    contents: @Composable () -> Unit,
)
