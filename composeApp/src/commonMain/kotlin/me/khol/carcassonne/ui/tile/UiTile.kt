package me.khol.carcassonne.ui.tile

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.addSvg
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import me.khol.carcassonne.Element
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.Tile
import me.khol.carcassonne.rotate
import org.jetbrains.compose.resources.DrawableResource

data class UiTile(
    val drawable: DrawableResource,
    val tile: Tile,
    val uiElements: Map<Element<*>, UiElement>,
) {
    data class UiElement(
        val shape: Shape,
        val figurePlacement: Offset,
    )
}

fun UiTile.rotate(rotation: Rotation) =
    copy(
        tile = tile.rotate(rotation),
        uiElements = uiElements.mapKeys { it.key.rotate(rotation) },
    )

fun svgToShape(
    pathData: String,
    svgWidth: Int = 512,
    svgHeight: Int = 512,
): Shape = object : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline = Outline.Generic(
        path = Path().apply {
            addSvg(pathData)
            transform(matrix = Matrix().apply { scale(size.width / svgWidth, size.height / svgHeight) })
        }
    )
}
