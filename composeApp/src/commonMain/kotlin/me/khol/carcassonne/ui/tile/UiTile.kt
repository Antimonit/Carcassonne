package me.khol.carcassonne.ui.tile

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape
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
