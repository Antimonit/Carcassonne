package me.khol.carcassonne.ui.tile.basic

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
    shapes = mapOf(
        svgToShape(basic_tile_P_city_svg) to P.city,
        svgToShape(basic_tile_P_road_svg) to P.road,
        svgToShape(basic_tile_P_field_top_svg) to P.fieldTopLeft,
        svgToShape(basic_tile_P_field_bottom_svg) to P.fieldBottomRight,
    ),
)
