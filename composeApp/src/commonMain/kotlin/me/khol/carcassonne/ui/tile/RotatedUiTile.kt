package me.khol.carcassonne.ui.tile

import me.khol.carcassonne.Rotation

data class RotatedUiTile(
    val uiTile: UiTile,
    val rotation: Rotation,
)

fun RotatedUiTile.asUiTile(): UiTile =
    uiTile.rotate(rotation)
