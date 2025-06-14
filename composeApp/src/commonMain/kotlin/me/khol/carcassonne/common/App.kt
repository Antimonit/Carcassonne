package me.khol.carcassonne.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colors.background,
        ) {
            PanningWindow {
                Text(
                    text = "Hello",
                )
            }
        }
    }
}
