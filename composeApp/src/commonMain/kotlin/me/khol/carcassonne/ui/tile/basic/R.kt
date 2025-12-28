package me.khol.carcassonne.ui.tile.basic

import androidx.compose.ui.geometry.Offset
import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.tile_basic_R
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.svgToShape

private const val basic_tile_R_city_svg = basic_tile_Q_city_svg
private const val basic_tile_R_field_svg = basic_tile_Q_field_svg

val R = UiTile(
    drawable = Res.drawable.tile_basic_R,
    tile = Tiles.Basic.R.tile,
    uiElements = mapOf(
        Tiles.Basic.R.city to UiTile.UiElement(
            shape = svgToShape(basic_tile_R_city_svg),
            figurePlacement = Offset(0.5f, 0.25f),
        ),
        Tiles.Basic.R.field to UiTile.UiElement(
            shape = svgToShape(basic_tile_R_field_svg),
            figurePlacement = Offset(0.5f, 0.85f),
        ),
    ),
)
