package me.khol.carcassonne.ui.tile.basic

import androidx.compose.ui.geometry.Offset
import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.tile_basic_N
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basic.N
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.svgToShape

private const val basic_tile_N_city_svg = basic_tile_M_city_svg
private const val basic_tile_N_field_svg = basic_tile_M_field_svg

val N = UiTile(
    drawable = Res.drawable.tile_basic_N,
    tile = Tiles.Basic.N,
    uiElements = mapOf(
        N.city to UiTile.UiElement(
            shape = svgToShape(basic_tile_N_city_svg),
            figurePlacement = Offset(0.75f, 0.25f),
        ),
        N.field to UiTile.UiElement(
            shape = svgToShape(basic_tile_N_field_svg),
            figurePlacement = Offset(0.3f, 0.7f),
        ),
    ),
)
