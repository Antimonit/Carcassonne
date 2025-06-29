package me.khol.carcassonne.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Immutable
private data class GridCoordinates(
    val x: Int,
    val y: Int,
)

private data class GridCoordinatesElement(
    val coordinates: GridCoordinates,
) : ModifierNodeElement<GridCoordinatesNode>() {

    override fun create() = GridCoordinatesNode(coordinates)

    override fun update(node: GridCoordinatesNode) {
        node.coordinates = coordinates
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "coordinates"
        value = coordinates
    }
}

private class GridCoordinatesNode(
    var coordinates: GridCoordinates,
) : ParentDataModifierNode, Modifier.Node() {

    override fun Density.modifyParentData(parentData: Any?): GridCoordinates = coordinates
}

object GridScope {

    @Stable
    fun Modifier.coordinates(x: Int, y: Int) =
        this.then(GridCoordinatesElement(coordinates = GridCoordinates(x, y)))
}

/**
 * Children in [content] must have the [GridScope.coordinates] modifier applied to them
 * otherwise they will not be laid out.
 */
@Composable
fun GridLayout(
    cellSize: Dp,
    cellSpacing: Dp,
    modifier: Modifier = Modifier,
    content: @Composable GridScope.() -> Unit,
) {
    Layout(
        content = {
            GridScope.content()
        },
        modifier = modifier,
    ) { measurables, constraints ->
        val size = cellSize.roundToPx()
        val tiling = (cellSize + cellSpacing).roundToPx()
        val tileConstraints = Constraints.fixed(size, size)
        val placeables = measurables
            .filter { it.parentData is GridCoordinates }
            .map { it.measure(tileConstraints) }
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                val coordinates = placeable.parentData as GridCoordinates
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
private fun GridLayoutPreview() {
    MaterialTheme {
        Surface {
            GridLayout(
                cellSize = 72.dp,
                cellSpacing = 12.dp,
                modifier = Modifier
                    .size(300.dp)
            ) {
                listOf(
                    GridCoordinates(0, 0) to Color.Magenta,
                    GridCoordinates(-1, 0) to Color.Blue,
                    GridCoordinates(0, 1) to Color.Red,
                    GridCoordinates(1, -1) to Color.Cyan,
                    GridCoordinates(1, 2) to Color.Green,
                ).forEach { (coordinates, color) ->
                    Box(
                        modifier = Modifier
                            .background(color)
                            .coordinates(coordinates.x, coordinates.y)
                    )
                }
            }
        }
    }
}
