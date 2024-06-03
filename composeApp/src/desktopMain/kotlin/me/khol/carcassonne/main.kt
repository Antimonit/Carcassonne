package me.khol.carcassonne

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import me.khol.carcassonne.common.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Carcassonne",
    ) {
        App()
    }
}
