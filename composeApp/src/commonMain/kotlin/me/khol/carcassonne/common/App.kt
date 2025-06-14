package me.khol.carcassonne.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.basic.D
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colors.background,
        ) {
            PanningWindow {
                Board(
                    board = Board
                        .starting(startingTile = D)
                        .placeTile(coordinates = Coordinates(-1, 0), tile = RotatedTile(D, Rotation.ROTATE_180))
                        .placeTile(coordinates = Coordinates(-2, 0), tile = RotatedTile(D, Rotation.ROTATE_180))
                        .placeTile(coordinates = Coordinates(-3, 0), tile = RotatedTile(D, Rotation.ROTATE_180))
                        .placeTile(coordinates = Coordinates(1, 0), tile = RotatedTile(D, Rotation.ROTATE_180))
                        .placeTile(coordinates = Coordinates(1, -1), tile = RotatedTile(D, Rotation.ROTATE_0))
                        .placeTile(coordinates = Coordinates(0, 1), tile = RotatedTile(D, Rotation.ROTATE_180))
                )
            }
        }
    }
}
