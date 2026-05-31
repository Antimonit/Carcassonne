package me.khol.carcassonne

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import me.khol.carcassonne.ui.App
import me.khol.carcassonne.ui.generated.resources.Res
import me.khol.carcassonne.ui.generated.resources.allDrawableResources
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    ComposeViewport {
        val figureResources = Res.allDrawableResources.values.map {
            preloadImage(it).value
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

@Composable
private fun preloadImage(
    resource: DrawableResource,
): State<Painter?> = remember(resource) {
    mutableStateOf<Painter?>(null)
}.apply {
    value = painterResource(resource).takeIf { it.intrinsicSize != Size.Unspecified }
}
