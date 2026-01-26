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
import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.figure_abbot_black
import carcassonne.composeapp.generated.resources.figure_abbot_blue
import carcassonne.composeapp.generated.resources.figure_abbot_green
import carcassonne.composeapp.generated.resources.figure_abbot_pink
import carcassonne.composeapp.generated.resources.figure_abbot_red
import carcassonne.composeapp.generated.resources.figure_abbot_yellow
import carcassonne.composeapp.generated.resources.figure_farmer_black
import carcassonne.composeapp.generated.resources.figure_farmer_blue
import carcassonne.composeapp.generated.resources.figure_farmer_green
import carcassonne.composeapp.generated.resources.figure_farmer_pink
import carcassonne.composeapp.generated.resources.figure_farmer_red
import carcassonne.composeapp.generated.resources.figure_farmer_yellow
import carcassonne.composeapp.generated.resources.figure_meeple_black
import carcassonne.composeapp.generated.resources.figure_meeple_blue
import carcassonne.composeapp.generated.resources.figure_meeple_green
import carcassonne.composeapp.generated.resources.figure_meeple_pink
import carcassonne.composeapp.generated.resources.figure_meeple_red
import carcassonne.composeapp.generated.resources.figure_meeple_yellow
import kotlinx.browser.document
import me.khol.carcassonne.ui.App
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.preloadImageBitmap

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val figureResources = listOf(
            Res.drawable.figure_abbot_black,
            Res.drawable.figure_abbot_blue,
            Res.drawable.figure_abbot_green,
            Res.drawable.figure_abbot_pink,
            Res.drawable.figure_abbot_red,
            Res.drawable.figure_abbot_yellow,
            Res.drawable.figure_farmer_black,
            Res.drawable.figure_farmer_blue,
            Res.drawable.figure_farmer_green,
            Res.drawable.figure_farmer_pink,
            Res.drawable.figure_farmer_red,
            Res.drawable.figure_farmer_yellow,
            Res.drawable.figure_meeple_black,
            Res.drawable.figure_meeple_blue,
            Res.drawable.figure_meeple_green,
            Res.drawable.figure_meeple_pink,
            Res.drawable.figure_meeple_red,
            Res.drawable.figure_meeple_yellow,
        ).map {
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