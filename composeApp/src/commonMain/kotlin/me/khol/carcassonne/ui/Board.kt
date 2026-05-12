package me.khol.carcassonne.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.History
import me.khol.carcassonne.Phase
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.PlacedTile
import me.khol.carcassonne.Player
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.elementToFeature
import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.fixtures.Players
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.ui.GridScope.coordinates
import me.khol.carcassonne.ui.tile.tileSize
import me.khol.carcassonne.ui.tile.tileSpacing
import me.khol.carcassonne.ui.tile.toUiTile

@Composable
fun Board(
    board: Board,
    phase: Phase,
    onPlaceTile: (Phase.PlacingTile, PlacedTile) -> Unit,
    onPlaceFigure: (Phase.PlacingFigure, PlacedFigure) -> Unit,
    modifier: Modifier = Modifier,
) {
    GridLayout(
        cellSize = tileSize,
        cellSpacing = tileSpacing,
        modifier = modifier,
    ) {
        board.tiles.forEach { (coordinates, rotatedTile) ->
            val rotatedUiTile = rotatedTile.toUiTile()
            RotatedTile(
                rotatedUiTile = rotatedUiTile,
                overlay = {
                    if (phase is Phase.Scoring)  {
                        TileScoringOverlay(
                            coordinates = coordinates,
                            scoringEvent = phase.scoringEvent,
                        )
                    }
                    TileFiguresOverlay(
                        figures = board.getFigures(coordinates),
                        rotatedUiTile = rotatedUiTile,
                    )
                },
                modifier = Modifier
                    .coordinates(coordinates)
            )
        }
        when (phase) {
            is Phase.PlacingFigure -> {
                val placingTile = phase.placedTile
                val rotatedUiTile = placingTile.rotatedTile.toUiTile()
                FigureSelectionPopupBox(
                    phase = phase,
                    onPlaceFigure = onPlaceFigure,
                    modifier = Modifier
                        .coordinates(placingTile.coordinates)
                        .zIndex(2f)
                ) {
                    RotatedTile(
                        rotatedUiTile = rotatedUiTile,
                        overlay = {
                            TileElementsOverlay(
                                onElementClick = { onPlaceFigure(phase, it) },
                                rotatedUiTile = rotatedUiTile,
                                validMeeplePlacements = phase.validFigurePlacements,
                            )
                            phase.placedFigure?.let {
                                TileFiguresOverlay(
                                    figures = listOf(it),
                                    rotatedUiTile = rotatedUiTile,
                                )
                            }
                        },
                    )
                }
            }
            is Phase.PlacingTile -> {
                val openSpaces = phase.validTilePlacements
                phase.placedTile?.let { placingTile ->
                    val possibilities = openSpaces.getValue(placingTile.coordinates)
                    val rotatedUiTile = placingTile.rotatedTile.toUiTile()
                    key(placingTile.coordinates) {
                        RotatedTile(
                            rotatedUiTile = rotatedUiTile,
                            modifier = Modifier
                                .coordinates(placingTile.coordinates)
                                .clickable {
                                    onPlaceTile(phase, possibilities[(possibilities.indexOf(placingTile) + 1) % possibilities.size])
                                }
                        )
                    }
                }
                openSpaces.forEach { (coordinates, placedTiles) ->
                    if (phase.placedTile?.coordinates != coordinates) {
                        key(coordinates) {
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Black.copy(alpha = 0.12f))
                                    .coordinates(coordinates)
                                    .clickable {
                                        onPlaceTile(phase, placedTiles.first())
                                    }
                            )
                        }
                    }
                }
            }
            is Phase.Scoring -> {
                // handled as an overlay
            }
            Phase.FinalScoring -> Unit
        }
    }
}

@Composable
fun SimpleBoard(
    board: Board,
    scoringEvent: History.Event.Scoring?,
    modifier: Modifier = Modifier,
) {
    GridLayout(
        cellSize = tileSize,
        cellSpacing = 4.dp,
        modifier = modifier,
    ) {
        board.tiles.forEach { (coordinates, tile) ->
            val rotatedUiTile = tile.toUiTile()
            RotatedTile(
                rotatedUiTile = rotatedUiTile,
                overlay = {
                    if (scoringEvent != null)  {
                        TileScoringOverlay(
                            coordinates = coordinates,
                            scoringEvent = scoringEvent,
                        )
                    }
                    TileFiguresOverlay(
                        figures = board.getFigures(coordinates),
                        rotatedUiTile = rotatedUiTile,
                    )
                },
                modifier = Modifier
                    .coordinates(coordinates)
            )
        }
    }
}

@Preview
@Composable
private fun BoardPreview() {
    MaterialTheme {
        Surface {
            val board = Board
                .starting(startingTile = Tiles.Basic.D.tile)
                .placeTile(
                    coordinates = Coordinates(-1, 0),
                    tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180),
                    placedFigures = listOf(
                        PlacedFigure(
                            placedElement = Tiles.Basic.D.road.rotated(Rotation.ROTATE_180).placed(-1, 0),
                            figure = PlayerFigures.greenMeeple,
                        ),
                    ),
                )
                .placeTile(coordinates = Coordinates(-2, 0), tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180), placedFigures = emptyList())
                .placeTile(coordinates = Coordinates(-3, 0), tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180), placedFigures = emptyList())
                .placeTile(coordinates = Coordinates(1, 0), tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180), placedFigures = emptyList())
                .placeTile(coordinates = Coordinates(1, -1), tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_0), placedFigures = emptyList())
                .placeTile(coordinates = Coordinates(0, 1), tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180), placedFigures = emptyList())
            val tile = Tiles.Basic.D.tile
            Board(
                board = board,
                phase = Phase.PlacingTile(
                    tile = tile,
                    validTilePlacements = board.possibleSpacesForTile(tile),
                ),
                onPlaceTile = { _, _ -> },
                onPlaceFigure = { _, _ -> },
            )
        }
    }
}

private class ScoringPlayersParameterProvider : PreviewParameterProvider<Set<Player>> {
    private val green = Players.green
    private val red = Players.red
    private val blue = Player("Blue", Player.Color.Blue)
    private val pink = Player("Pink", Player.Color.Pink)
    override val values = sequenceOf(
        setOf(green),
        setOf(blue),
        setOf(blue, pink),
        setOf(green, pink, red),
    )
}

@Preview(widthDp = 400, heightDp = 400)
@Composable
private fun BoardScoringPreview(
    @PreviewParameter(ScoringPlayersParameterProvider::class)
    scoringPlayers: Set<Player>
) {
    MaterialTheme {
        Surface {
            val placedFigure = PlacedFigure(
                placedElement = Tiles.Basic.P.city.rotated(Rotation.ROTATE_180).placed(0, -1),
                figure = PlayerFigures.greenMeeple,
            )
            val placedFigure2 = PlacedFigure(
                placedElement = Tiles.Basic.D.road.rotated(Rotation.ROTATE_270).placed(1, -1),
                figure = PlayerFigures.redMeeple,
            )
            val board = Board
                .starting(startingTile = Tiles.Basic.D.tile)
                .placeTile(
                    coordinates = Coordinates(0, -1),
                    tile = Tiles.Basic.P.tile.rotated(Rotation.ROTATE_180),
                    placedFigures = listOf(placedFigure),
                )
                .placeTile(
                    coordinates = Coordinates(1, -1),
                    tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_270),
                    placedFigures = listOf(placedFigure2),
                )
            Board(
                board = board,
                phase = Phase.Scoring(
                    History.Event.Scoring(
                        triggerPlayer = Players.green,
                        scoringPlayers = scoringPlayers,
                        feature = board.elementToFeature(placedFigure.placedElement),
                        figures = setOf(placedFigure),
                        points = 6,
                        board = board,
                    )
                ),
                onPlaceTile = { _, _ -> },
                onPlaceFigure = { _, _ -> },
            )
        }
    }
}

fun Modifier.coordinates(coordinates: Coordinates): Modifier =
    this.coordinates(x = coordinates.x, y = coordinates.y)
