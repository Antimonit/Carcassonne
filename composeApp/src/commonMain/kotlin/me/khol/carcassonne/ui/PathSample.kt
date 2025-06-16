package me.khol.carcassonne.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun PathSample() {
    val square = Path().apply {
        addRoundRect(roundRect = RoundRect(-10f, 2f, 10f, -2f, CornerRadius(1f, 1f)))
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
                        phase = phase,
                        advance = 30f
                    )
                )
            )

            drawCircle(color = Color.Black, radius = 5f, center = Offset(350f, -50f))
            drawCircle(color = Color.Black, radius = 5f, center = Offset(150f, 100f))
            drawCircle(color = Color.Black, radius = 5f, center = Offset(120f, -40f))
            drawCircle(color = Color.Black, radius = 5f, center = Offset(450f, 30f))

            drawPath(
                path = Path().apply {
                    moveTo(350f, -50f)
                    cubicTo(120f, -40f, 150f, 100f, 450f, 30f)
                    close()
                },
                color = Color.Red,
                style = Stroke(
                    pathEffect = PathEffect.stampedPathEffect(
                        shape = square,
                        style = StampedPathEffectStyle.Morph,
                        phase = phase,
                        advance = 30f,
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
                        phase = phase,
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
                        phase = phase,
                        advance = 45f
                    )
                )
            )
        }
    }
}