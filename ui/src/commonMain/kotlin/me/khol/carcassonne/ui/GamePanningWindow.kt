package me.khol.carcassonne.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import me.khol.carcassonne.Game
import me.khol.carcassonne.Phase
import me.khol.carcassonne.ui.tile.tileSize
import me.khol.carcassonne.ui.tile.tileSpacing

@Composable
fun GamePanningWindow(
    game: Game,
    modifier: Modifier = Modifier,
    contents: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val spacing = with(density) { (tileSize + tileSpacing).toPx() }

    val state = rememberPanZoomState()

    var containerSize by remember { mutableStateOf(IntSize.Zero) }

    val phase = game.phase
    val isPlacingTile = phase is Phase.PlacingTile
    val isPlacingFigure = phase is Phase.PlacingFigure
    val isScoring = phase is Phase.Scoring
    val scoringTarget = if (isScoring) phase.scoringEvent else null

    suspend fun scrollToBounds(bounds: Rect) {
        val zoom = run {
            // Calculate bounding box of all tiles in pixel coordinates:
            // * Tiles are centered at their `coordinate * spacing`.
            // * Each tile extends 0.5 * spacing from its center.
            // * Add padding of 0.25 * spacing so that the tiles are not crammed to the edge of the screen.
            val boardRect = bounds.inflate(0.75f * spacing)

            // Calculate the minimum zoom to fit the board rect in the container
            val zoomX = containerSize.width / boardRect.width
            val zoomY = containerSize.height / boardRect.height
            minOf(zoomX, zoomY)
        }

        state.scrollTo(
            offset = bounds.center,
            zoom = zoom,
        )
    }

    LaunchedEffect(isPlacingTile, isPlacingFigure, scoringTarget, containerSize) {
        if (isScoring) {
            val placedElements = phase.scoringEvent.feature.placedElements
            val bounds = Rect(
                left = placedElements.minOf { it.coordinates.x } * spacing,
                top = placedElements.minOf { it.coordinates.y } * spacing,
                right = placedElements.maxOf { it.coordinates.x } * spacing,
                bottom = placedElements.maxOf { it.coordinates.y } * spacing,
            )

            scrollToBounds(bounds)
        } else if (isPlacingFigure) {
            val coordinates = phase.placedTile.coordinates
            val bounds = Rect(
                center = Offset(
                    x = coordinates.x * spacing,
                    y = coordinates.y * spacing,
                ),
                radius = spacing / 2f,
            )

            scrollToBounds(bounds)

        } else if (isPlacingTile && containerSize != IntSize.Zero) {
            val keys = game.board.possibleSpacesForTile(phase.tile).keys + game.board.tiles.keys
            val bounds = Rect(
                left = keys.minOf { it.x } * spacing,
                top = keys.minOf { it.y } * spacing,
                right = keys.maxOf { it.x } * spacing,
                bottom = keys.maxOf { it.y } * spacing,
            )

            state.setPanBounds(bounds)

            scrollToBounds(bounds)
        }
    }

    PanningWindow(
        state = state,
        modifier = modifier.onSizeChanged { containerSize = it },
        contents = contents,
    )
}
