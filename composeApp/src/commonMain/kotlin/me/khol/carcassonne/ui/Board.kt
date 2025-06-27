package me.khol.carcassonne.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Phase
import me.khol.carcassonne.PlacedTile
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.ui.GridScope.coordinates
import me.khol.carcassonne.ui.tile.tileSize
import me.khol.carcassonne.ui.tile.toUiTile
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Board(
    board: Board,
    phase: Phase,
    onPlaceTile: (Coordinates, PlacedTile) -> Unit,
    modifier: Modifier = Modifier,
) {
    GridLayout(
        cellSize = tileSize,
        cellSpacing = 4.dp,
        modifier = modifier,
    ) {
        board.tiles.forEach { (coordinates, tile) ->
            Tile(
                drawable = tile.tile.toUiTile().drawable,
                rotation = tile.rotation,
                modifier = Modifier
                    .coordinates(coordinates)
            )
        }
        when (phase) {
            is Phase.PlacingFigure -> {
                val placingTile = phase.tile
                val uiTile = placingTile.rotatedTile.tile.toUiTile()
                val rotation = placingTile.rotatedTile.rotation
                Tile(
                    drawable = uiTile.drawable,
                    rotation = rotation,
                    overlay = {
                        TileElementsOverlay(
                            onElementClick = {
                                println("Clicked: ${it.rotate(rotation)}")
                            },
                            uiTile = uiTile,
                        )
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
                    val uiTile = tile.toUiTile()
                    val rotation = placingTile.rotatedTile.rotation
                    Tile(
                        drawable = uiTile.drawable,
                        rotation = rotation,
                        modifier = Modifier
                            .coordinates(placingTile.coordinates)
                            .clickable {
                                onPlaceTile(placingTile.coordinates, possibilities[(possibilities.indexOf(placingTile) + 1) % possibilities.size])
                            }
                    )
                }
                openSpaces.forEach { (coordinates, placedTiles) ->
                    key(coordinates) {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Black.copy(alpha = 0.12f))
                                .coordinates(coordinates)
                                .clickable {
                                    onPlaceTile(coordinates, placedTiles.first())
                                }
                        )
                    }
                }
            }
            Phase.Scoring -> Unit
            Phase.FinalScoring -> Unit
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
                    .starting(startingTile = Tiles.Basic.D)
                    .placeTile(coordinates = Coordinates(-1, 0), tile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_180))
                    .placeTile(coordinates = Coordinates(-2, 0), tile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_180))
                    .placeTile(coordinates = Coordinates(-3, 0), tile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_180))
                    .placeTile(coordinates = Coordinates(1, 0), tile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_180))
                    .placeTile(coordinates = Coordinates(1, -1), tile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_0))
                    .placeTile(coordinates = Coordinates(0, 1), tile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_180)),
                phase = Phase.PlacingTile.Fresh(Tiles.Basic.D),
                onPlaceTile = { coordinates, tile -> },
            )
        }
    }
}

private fun Modifier.coordinates(coordinates: Coordinates): Modifier =
    this.coordinates(x = coordinates.x, y = coordinates.y)
