package me.khol.carcassonne

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import me.khol.carcassonne.common.App
import org.jetbrains.compose.ui.tooling.preview.Preview

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Carcassonne",
    ) {
        App()
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    MaterialTheme {
        Surface {
            App()
        }
    }
}
