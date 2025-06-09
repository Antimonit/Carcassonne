package me.khol.carcassonne.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun App() {
    PanningWindow {
        Text(
            text = "Hello",
        )
    }
}
