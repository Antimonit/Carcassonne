package me.khol.carcassonne.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun Board(
    tiles: List<PlacedTile>,
) {
//    Box(
//        Modifier.fillMaxSize()
//    ) {
//        val tileSize = 128.dp
//        tiles.forEach { tile ->
//            Tile(
//                placedTile = tile,
//                modifier = Modifier.offset(tileSize * tile.x, tileSize * tile.y)
//            )
//        }
//    }

    val size = 5f
    val square = Path().apply {
        addOval(Rect(center = Offset.Zero, radius = 5f))
//        lineTo(size, 0f)
//        lineTo(size, size)
//        lineTo(0f, size)
//        close()
    }
    Column(modifier = Modifier.fillMaxHeight().wrapContentSize(Alignment.Center)) {
        val canvasModifier = Modifier.requiredSize(80.dp).align(Alignment.CenterHorizontally)

        val transition = rememberInfiniteTransition()
        val phase by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(20000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
        )

        // StampedPathEffectStyle.Morph will modify the lines of the square to be curved to fit
        // the curvature of the circle itself. Each stamped square will be rendered as an arc
        // that is fully contained by the bounds of the circle itself
        Canvas(modifier = canvasModifier) {
            drawCircle(color = Color.Blue)
            drawCircle(
                color = Color.Red,
                style = Stroke(
                    pathEffect = PathEffect.stampedPathEffect(
                        shape = square,
                        style = StampedPathEffectStyle.Morph,
                        phase = 0f,
                        advance = 20f
                    )
                )
            )

            drawPath(
                path = Path().apply {
                    cubicTo(50f, 100f, 20f, -40f, 450f, 30f)
                },
                color = Color.Red,
                style = Stroke(
                    pathEffect = PathEffect.stampedPathEffect(
                        shape = square,
                        style = StampedPathEffectStyle.Morph,
                        phase = phase,
                        advance = 20f
                    )
                )
            )
        }

        Spacer(modifier = Modifier.requiredSize(10.dp))

        // StampedPathEffectStyle.Rotate will draw the square repeatedly around the circle
        // such that each stamped square is centered on the circumference of the circle and is
        // rotated along the curvature of the circle itself
        Canvas(modifier = canvasModifier) {
            drawCircle(color = Color.Blue)
            drawCircle(
                color = Color.Red,
                style = Stroke(
                    pathEffect = PathEffect.stampedPathEffect(
                        shape = square,
                        style = StampedPathEffectStyle.Rotate,
                        phase = 0f,
                        advance = 30f
                    )
                )
            )
        }

        Spacer(modifier = Modifier.requiredSize(10.dp))

        // StampedPathEffectStyle.Translate will draw the square repeatedly around the circle
        // with the top left of each stamped square on the circumference of the circle
        Canvas(modifier = canvasModifier) {
            drawCircle(color = Color.Blue)
            drawCircle(
                color = Color.Red,
                style = Stroke(
                    pathEffect = PathEffect.stampedPathEffect(
                        shape = square,
                        style = StampedPathEffectStyle.Translate,
                        phase = 0f,
                        advance = 45f
                    )
                )
            )
        }
    }
}
