package me.khol.carcassonne.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import me.khol.carcassonne.PlacedTile
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.Tile
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.ui.tile.tileSize
import me.khol.carcassonne.ui.tile.toUiTile
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
    currentTile: Tile?,
    placingTile: PlacedTile?,
    onPlaceTile: (Coordinates, PlacedTile) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoardLayout(
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
        currentTile?.let { tile ->
            val openSpaces = remember(board, tile) { board.possibleSpacesForTile(tile) }
            if (placingTile != null) {
                val possibilities = openSpaces.getValue(placingTile.coordinates)
                Tile(
                    drawable = tile.toDrawable(),
                    rotation = placingTile.rotatedTile.rotation,
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
        val size = tileSize.roundToPx()
        val tiling = (tileSize + 4.dp).roundToPx()
        val tileConstraints = Constraints.fixed(size, size)
        val placeables = measurables.map { measurable ->
            measurable.measure(tileConstraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                val coordinates = placeable.parentData as Coordinates
                placeable.placeRelative(
                    x = (constraints.maxWidth - size) / 2 + coordinates.x * tiling,
                    y = (constraints.maxHeight - size) / 2 + coordinates.y * tiling,
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
                    .starting(startingTile = Tiles.Basic.D)
                    .placeTile(coordinates = Coordinates(-1, 0), tile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_180))
                    .placeTile(coordinates = Coordinates(-2, 0), tile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_180))
                    .placeTile(coordinates = Coordinates(-3, 0), tile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_180))
                    .placeTile(coordinates = Coordinates(1, 0), tile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_180))
                    .placeTile(coordinates = Coordinates(1, -1), tile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_0))
                    .placeTile(coordinates = Coordinates(0, 1), tile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_180)),
                currentTile = Tiles.Basic.D,
                placingTile = null,
                onPlaceTile = { coordinates, tile -> },
            )
        }
    }
}
