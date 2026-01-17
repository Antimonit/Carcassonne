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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Phase
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.PlacedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.fixtures.PlayerFigures
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
    onPlaceTile: (PlacedTile) -> Unit,
    onPlaceFigure: (PlacedTile, PlacedFigure) -> Unit,
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
                RotatedTile(
                    rotatedUiTile = rotatedUiTile,
                    overlay = {
                        TileElementsOverlay(
                            onElementClick = {
                                onPlaceFigure(placingTile, it)
                            },
                            rotatedUiTile = rotatedUiTile,
                            validMeeplePlacements = phase.validFigurePlacements,
                        )
                        when (phase) {
                            is Phase.PlacingFigure.Fresh -> Unit
                            is Phase.PlacingFigure.Placed -> {
                                TileFiguresOverlay(
                                    figures = listOf(phase.placedFigure),
                                    rotatedUiTile = rotatedUiTile,
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .coordinates(placingTile.coordinates)
                )
            }
            is Phase.PlacingTile -> {
                val tile = phase.tile
                val openSpaces = remember(board, tile) { board.possibleSpacesForTile(tile) }
                if (phase is Phase.PlacingTile.Placed) {
                    val placingTile = phase.placedTile
                    val possibilities = openSpaces.getValue(placingTile.coordinates)
                    val rotatedUiTile = placingTile.rotatedTile.toUiTile()
                    key(placingTile.coordinates) {
                        RotatedTile(
                            rotatedUiTile = rotatedUiTile,
                            modifier = Modifier
                                .coordinates(placingTile.coordinates)
                                .clickable {
                                    onPlaceTile(possibilities[(possibilities.indexOf(placingTile) + 1) % possibilities.size])
                                }
                        )
                    }
                }
                openSpaces.forEach { (coordinates, placedTiles) ->
                    if (phase !is Phase.PlacingTile.Placed || phase.placedTile.coordinates != coordinates) {
                        key(coordinates) {
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Black.copy(alpha = 0.12f))
                                    .coordinates(coordinates)
                                    .clickable {
                                        onPlaceTile(placedTiles.first())
                                    }
                            )
                        }
                    }
                }
            }
            Phase.Scoring -> Unit
            Phase.FinalScoring -> Unit
        }
    }
}

@Composable
fun SimpleBoard(
    board: Board,
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
            Board(
                board = Board
                    .starting(startingTile = Tiles.Basic.D.tile)
                    .placeTile(
                        coordinates = Coordinates(-1, 0),
                        tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180),
                        placedFigures = listOf(
                            PlacedFigure(
                                placedElement = Tiles.Basic.D.road.rotated(Rotation.ROTATE_180).placed(Coordinates(-1, 0)),
                                figure = PlayerFigures.greenMeeple,
                            ),
                        ),
                    )
                    .placeTile(coordinates = Coordinates(-2, 0), tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180), placedFigures = emptyList())
                    .placeTile(coordinates = Coordinates(-3, 0), tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180), placedFigures = emptyList())
                    .placeTile(coordinates = Coordinates(1, 0), tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180), placedFigures = emptyList())
                    .placeTile(coordinates = Coordinates(1, -1), tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_0), placedFigures = emptyList())
                    .placeTile(coordinates = Coordinates(0, 1), tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180), placedFigures = emptyList()),
                phase = Phase.PlacingTile.Fresh(Tiles.Basic.D.tile),
                onPlaceTile = { tile -> },
                onPlaceFigure = { tile, figure -> },
            )
        }
    }
}

fun Modifier.coordinates(coordinates: Coordinates): Modifier =
    this.coordinates(x = coordinates.x, y = coordinates.y)
