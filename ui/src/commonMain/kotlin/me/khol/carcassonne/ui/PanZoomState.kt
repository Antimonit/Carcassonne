package me.khol.carcassonne.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
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

    private val panAnimatable = Animatable(Offset.Zero, Offset.VectorConverter)
    private val zoomAnimatable = Animatable(1f)

    val pan: Offset
        get() = panAnimatable.value

    val zoom: Float
        get() = zoomAnimatable.value

    init {
        zoomAnimatable.updateBounds(0.4f, 2.0f)
    }

    /**
     * Center of the viewport should stay within [bounds].
     */
    fun setPanBounds(bounds: Rect) {
        panAnimatable.updateBounds(bounds.topLeft, bounds.bottomRight)
    }

    suspend fun scrollTo(offset: Offset, zoom: Float) {
        coroutineScope {
            launch { panAnimatable.animateTo(offset, animationSpec = spring(stiffness = Spring.StiffnessLow, visibilityThreshold = Offset(0.0001f, 0.0001f))) }
            launch { zoomAnimatable.animateTo(zoom, animationSpec = spring(stiffness = Spring.StiffnessLow, visibilityThreshold = 0.0001f)) }
        }
    }

    suspend fun snapTo(offset: Offset, zoom: Float) {
        panAnimatable.snapTo(offset)
        zoomAnimatable.snapTo(zoom)
    }
}
