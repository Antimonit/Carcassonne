package me.khol.carcassonne.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import me.khol.carcassonne.common.tile.BasicSet
import me.khol.carcassonne.common.tile.BasicSetPreview
import me.khol.carcassonne.common.tile.Rotation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App() {
    PanningWindow {
        BasicSetPreview()
//        Board(
//            listOf(
//                PlacedTile(GridPosition(x = 1, y = 1), BasicSet.W, Rotation.ROTATE_180),
//                PlacedTile(GridPosition(x = 2, y = 1), BasicSet.B, Rotation.ROTATE_0),
//                PlacedTile(GridPosition(x = 3, y = 1), BasicSet.C, Rotation.ROTATE_0),
//                PlacedTile(GridPosition(x = 3, y = 2), BasicSet.D, Rotation.ROTATE_0),
//                PlacedTile(GridPosition(x = 4, y = 1), BasicSet.D, Rotation.ROTATE_0),
//                PlacedTile(GridPosition(x = 4, y = 2), BasicSet.D, Rotation.ROTATE_0),
//                PlacedTile(GridPosition(x = 3, y = 0), BasicSet.D, Rotation.ROTATE_0),
//                PlacedTile(GridPosition(x = 1, y = 0), BasicSet.X, Rotation.ROTATE_90),
//                PlacedTile(GridPosition(x = 0, y = 0), BasicSet.A, Rotation.ROTATE_270),
//            )
//        )
    }
}

@Preview
@Composable
fun AppCommonPreview() {
    MaterialTheme {
        Surface {
            App()
        }
    }
}
