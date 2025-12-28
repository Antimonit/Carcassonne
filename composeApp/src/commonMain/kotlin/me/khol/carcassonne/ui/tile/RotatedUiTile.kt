package me.khol.carcassonne.ui.tile

import me.khol.carcassonne.RotatedElement
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.rotated

data class RotatedUiTile(
    val uiTile: UiTile,
    val rotation: Rotation,
) {

    val rotatedUiElements: Map<RotatedElement<*>, UiTile.UiElement>
        get() = uiTile.uiElements.mapKeys {
            it.key.rotated(rotation)
        }
}
