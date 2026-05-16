package me.khol.carcassonne.ui.tile

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape
import me.khol.carcassonne.Element
import me.khol.carcassonne.Tile
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
