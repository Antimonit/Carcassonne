package me.khol.carcassonne.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.common.tile.TileSurface
import me.khol.carcassonne.common.tile.tileSize
import me.khol.carcassonne.tiles.basic.D
import org.jetbrains.compose.ui.tooling.preview.Preview

private data class BoardCoordinatesElement(
    val coordinates: Coordinates,
) : ModifierNodeElement<BoardCoordinatesNode>() {

    override fun create() = BoardCoordinatesNode(coordinates)

    override fun update(node: BoardCoordinatesNode) {
        node.coordinates = coordinates
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "coordinates"
        value = coordinates
    }
}

private class BoardCoordinatesNode(
    var coordinates: Coordinates,
) : ParentDataModifierNode, Modifier.Node() {

    override fun Density.modifyParentData(parentData: Any?): Coordinates = coordinates
}

object BoardScope {

    @Stable
    fun Modifier.coordinates(coordinates: Coordinates) =
        this.then(BoardCoordinatesElement(coordinates = coordinates))
}

@Composable
fun Board(
    board: Board,
    modifier: Modifier = Modifier,
) {
    BoardLayout(
        modifier = modifier,
    ) {
        board.tiles.forEach { (coordinates, tile) ->
            TileSurface(
                modifier = Modifier.coordinates(coordinates)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(tile.tile.name)
                }
            }
        }
        board.openSpaces.forEach { coordinates ->
            Surface(
                color = Color.Black.copy(alpha = 0.12f),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .coordinates(coordinates)
                    .padding(4.dp)
            ) {
            }
        }
    }
}

@Composable
private fun BoardLayout(
    modifier: Modifier = Modifier,
    content: @Composable BoardScope.() -> Unit,
) {
    Layout(
        content = {
            BoardScope.content()
        },
        modifier = modifier,
    ) { measurables, constraints ->
        val tileSize = tileSize.roundToPx()
        val tileConstraints = Constraints.fixed(tileSize, tileSize)
        val placeables = measurables.map { measurable ->
            measurable.measure(tileConstraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                val coordinates = placeable.parentData as Coordinates
                placeable.placeRelative(
                    x = constraints.maxWidth / 2 + coordinates.x * tileSize - tileSize / 2,
                    y = constraints.maxHeight / 2 - coordinates.y * tileSize + tileSize / 2,
                )
            }
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
                    .starting(startingTile = D)
                    .placeTile(coordinates = Coordinates(-1, 0), tile = RotatedTile(D, Rotation.ROTATE_180))
                    .placeTile(coordinates = Coordinates(-2, 0), tile = RotatedTile(D, Rotation.ROTATE_180))
                    .placeTile(coordinates = Coordinates(-3, 0), tile = RotatedTile(D, Rotation.ROTATE_180))
                    .placeTile(coordinates = Coordinates(1, 0), tile = RotatedTile(D, Rotation.ROTATE_180))
                    .placeTile(coordinates = Coordinates(1, -1), tile = RotatedTile(D, Rotation.ROTATE_0))
                    .placeTile(coordinates = Coordinates(0, 1), tile = RotatedTile(D, Rotation.ROTATE_180)),
                modifier = Modifier
                    .background(Color.Gray)
            )
        }
    }
}
