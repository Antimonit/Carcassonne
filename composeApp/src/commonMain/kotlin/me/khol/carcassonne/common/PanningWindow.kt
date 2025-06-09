package me.khol.carcassonne.common

import androidx.compose.runtime.Composable

@Composable
expect fun PanningWindow(
    contents: @Composable () -> Unit,
)
