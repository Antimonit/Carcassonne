package me.khol.carcassonne.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberPanZoomState() =
    remember { PanZoomState() }

/**
 * State for controlling pan and zoom in a [PanningWindow].
 */
@Stable
class PanZoomState {

    internal val panAnimatable = Animatable(Offset.Zero, Offset.VectorConverter)
    internal val zoomAnimatable = Animatable(1f)

    val pan: Offset
        get() = panAnimatable.value

    val zoom: Float
        get() = zoomAnimatable.value

    suspend fun scrollTo(offset: Offset, zoom: Float) {
        coroutineScope {
            launch { panAnimatable.animateTo(offset, animationSpec = spring()) }
            launch { zoomAnimatable.animateTo(zoom, animationSpec = spring()) }
        }
    }
}
