package me.khol.carcassonne

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import me.khol.carcassonne.figure.Abbot
import me.khol.carcassonne.figure.Meeple
import me.khol.carcassonne.ui.App
import me.khol.carcassonne.ui.drawable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.preloadImageBitmap

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val figureResources = allFigureDrawables.map {
            preloadImageBitmap(it).value
        }

        val loaded = figureResources.count { it != null }

        if (loaded == figureResources.size) {
            App()
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            ) {
                Text("Preloading resources")
                LinearProgressIndicator(
                    progress = { loaded / figureResources.size.toFloat() }
                )
            }
        }
    }
}

private val allFigureDrawables: List<DrawableResource>
    get() = Player.Color.entries
        .map { Player(it.name, it) }
        .flatMap {
            // TODO: This is pretty brittle.
            listOf(
                Meeple.drawable(it, true),
                Meeple.drawable(it, false),
                Abbot.drawable(it, false),
            )
        }
