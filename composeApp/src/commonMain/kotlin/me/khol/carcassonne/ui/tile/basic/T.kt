package me.khol.carcassonne.ui.tile.basic

import androidx.compose.ui.geometry.Offset
import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.tile_basic_T
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basic.T
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.svgToShape

private const val basic_tile_T_city_svg = basic_tile_S_city_svg
private const val basic_tile_T_field_left_svg = basic_tile_S_field_left_svg
private const val basic_tile_T_field_right_svg = basic_tile_S_field_right_svg
private const val basic_tile_T_road_svg = basic_tile_S_road_svg

val T = UiTile(
    drawable = Res.drawable.tile_basic_T,
    tile = Tiles.Basic.T,
    uiElements = mapOf(
        T.city to UiTile.UiElement(
            shape = svgToShape(basic_tile_T_city_svg),
            figurePlacement = Offset(0.5f, 0.25f),
        ),
        T.fieldLeft to UiTile.UiElement(
            shape = svgToShape(basic_tile_T_field_left_svg),
            figurePlacement = Offset(0.25f, 0.85f),
        ),
        T.fieldRight to UiTile.UiElement(
            shape = svgToShape(basic_tile_T_field_right_svg),
            figurePlacement = Offset(0.75f, 0.85f),
        ),
        T.road to UiTile.UiElement(
            shape = svgToShape(basic_tile_T_road_svg),
            figurePlacement = Offset(0.5f, 0.82f),
        ),
    ),
)
