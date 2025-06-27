package me.khol.carcassonne.ui.tile.basic

import androidx.compose.ui.geometry.Offset
import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.tile_basic_P
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basic.P
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.svgToShape

const val basic_tile_P_city_svg = basic_tile_O_city_svg
const val basic_tile_P_road_svg = basic_tile_O_road_svg
const val basic_tile_P_field_top_svg = basic_tile_O_field_top_svg
const val basic_tile_P_field_bottom_svg = basic_tile_O_field_bottom_svg

val P = UiTile(
    drawable = Res.drawable.tile_basic_P,
    tile = Tiles.Basic.P,
    uiElements = mapOf(
        P.city to UiTile.UiElement(
            shape = svgToShape(basic_tile_P_city_svg),
            figurePlacement = Offset(0.4f, 0.2f),
        ),
        P.road to UiTile.UiElement(
            shape = svgToShape(basic_tile_P_road_svg),
            figurePlacement = Offset(0.64f, 0.68f),
        ),
        P.fieldTopLeft to UiTile.UiElement(
            shape = svgToShape(basic_tile_P_field_top_svg),
            figurePlacement = Offset(0.32f, 0.85f),
        ),
        P.fieldBottomRight to UiTile.UiElement(
            shape = svgToShape(basic_tile_P_field_bottom_svg),
            figurePlacement = Offset(0.85f, 0.85f),
        ),
    ),
)
