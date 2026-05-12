package me.khol.carcassonne.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.History
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.Player
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.elementToFeature
import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.fixtures.Players
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.ui.hud.uiColor
import me.khol.carcassonne.ui.tile.tileSize
import me.khol.carcassonne.ui.tile.tileSpacing
import me.khol.carcassonne.ui.tile.toUiTile

@Composable
fun TileScoringOverlay(
    coordinates: Coordinates,
    scoringEvent: History.Event.Scoring,
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition()
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing,
            ),
        ),
        label = "band animation"
    )

    scoringEvent.feature.placedElements
        .filter { it.coordinates == coordinates }
        .forEach { placedElement ->
            val coordinates = placedElement.coordinates
            val rotatedTile = scoringEvent.board.tiles.getValue(coordinates)
            val uiTile = rotatedTile.toUiTile()
            val rotation = rotatedTile.rotation
            val uiElement = uiTile.rotatedUiElements.getValue(placedElement.rotatedElement)
            val shape = uiElement.shape

            Box(
                modifier = modifier
                    .coordinates(coordinates)
                    .fillMaxSize()
                    .clip(shape = shape)
                    .background(
                        brush = linearBrush(
                            density = LocalDensity.current,
                            scoringEvent = scoringEvent,
                            rotation = rotation,
                            coordinates = coordinates,
                            progress = progress,
                        ),
                        shape = shape,
                    )
            )
        }
}

private fun linearBrush(
    density: Density,
    scoringEvent: History.Event.Scoring,
    rotation: Rotation,
    coordinates: Coordinates,
    progress: Float,
): Brush {
    val tileSizePx = with(density) { tileSize.toPx() }
    val tileSizeSpacingPx = with(density) { tileSize.toPx() + tileSpacing.toPx() }

    val stops = stops(scoringEvent.scoringPlayers)
    val bandWidth = with(density) { 4.dp.toPx() } * stops.size

    val elements = scoringEvent.feature.placedElements
    // Calculating the center coordinate of the feature is not really useful because
    // of the translational symmetry of the linear gradient.
    val centerCoordinate = Offset(
        x = (elements.minOf { it.coordinates.x } + elements.maxOf { it.coordinates.x }) / 2f,
        y = (elements.minOf { it.coordinates.y } + elements.maxOf { it.coordinates.y }) / 2f,
    )
    val bandOrientation = Offset(1f, 1f).rotate(rotation)

    val start = Offset(
        x = tileSizePx / 2f,
        y = tileSizePx / 2f,
    ) - Offset(
        x = tileSizeSpacingPx * (coordinates.x - centerCoordinate.x),
        y = tileSizeSpacingPx * (coordinates.y - centerCoordinate.y),
    ).rotate(rotation)
    val end = bandOrientation * bandWidth

    return Brush.linearGradient(
        colorStops = stops.toTypedArray(),
        start = start + end * progress,
        end = start + end * (1f + progress),
        tileMode = TileMode.Repeated,
    )
}

private fun Offset.rotate(rotation: Rotation): Offset =
    when (rotation) {
        Rotation.ROTATE_0 -> this
        Rotation.ROTATE_90 -> Offset(x = y, y = -x)
        Rotation.ROTATE_180 -> Offset(x = -x, y = -y)
        Rotation.ROTATE_270 -> Offset(x = -y, y = x)
    }

private fun stops(
    scoringPlayers: Set<Player>,
): List<Pair<Float, Color>> {
    val scoringPlayerColor = scoringPlayers
        .map { it.color.uiColor().copy(alpha = 0.85f) }

    val colors = if (scoringPlayerColor.size > 1) {
        scoringPlayerColor
    } else {
        scoringPlayerColor + Color.White.copy(alpha = 0.25f)
    }
    val bandCount = colors.size
    return colors
        .flatMapIndexed { index, color ->
            listOf(
                (1f / bandCount) * index to color,
                (1f / bandCount) * (index + 1) to color,
            )
        }
}

@Preview
@Composable
private fun TileScoringOverlayPreview() {
    MaterialTheme {
        val placedFigure = PlacedFigure(
            placedElement = Tiles.Basic.D.city.rotated(Rotation.ROTATE_180).placed(0, -1),
            figure = PlayerFigures.greenMeeple,
        )
        val board = Board
            .starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(
                coordinates = Coordinates(x = 0, y = -1),
                tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180),
                placedFigures = listOf(placedFigure),
            )
        Box(
            modifier = Modifier
                .size(120.dp)
        ) {
            TileScoringOverlay(
                coordinates = Coordinates(0, 0),
                scoringEvent = History.Event.Scoring(
                    triggerPlayer = Players.green,
                    scoringPlayers = setOf(Players.green),
                    feature = board.elementToFeature(placedFigure.placedElement),
                    figures = setOf(placedFigure),
                    points = 4,
                    board = board,
                )
            )
        }
    }
}
