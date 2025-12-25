package me.khol.carcassonne.ui.tile

import me.khol.carcassonne.Rotation
import me.khol.carcassonne.rotate

data class RotatedUiTile(
    val uiTile: UiTile,
    val rotation: Rotation,
)

fun RotatedUiTile.asUiTile(): UiTile =
    uiTile.copy(
        tile = uiTile.tile.copy(
            edges = uiTile.tile.edges.rotate(rotation),
            elements = uiTile.tile.elements.rotate(rotation),
        ),
        uiElements = uiTile.uiElements.mapKeys { it.key.rotate(rotation) },
    )
