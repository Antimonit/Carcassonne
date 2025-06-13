package me.khol.carcassonne.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun Offset.rotateBy(angle: Float): Offset {
    val angleInRadians = angle * (PI / 180)
    val cos = cos(angleInRadians)
    val sin = sin(angleInRadians)
    return Offset((x * cos - y * sin).toFloat(), (x * sin + y * cos).toFloat())
}

@Composable
expect fun PanningWindow(
    contents: @Composable () -> Unit,
)
