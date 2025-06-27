package me.khol.carcassonne.ui.tile.basic

import androidx.compose.ui.geometry.Offset
import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.tile_basic_V_G
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basic.V_G
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.svgToShape

private const val basic_tile_V_G_garden_svg = "m 339.30469,12.744141 -140.43555,63.484375 -0.24023,12.746093 164.00195,77.191411 h 11.54297 L 496.81445,99.074219 V 85.0625 Z"
private const val basic_tile_V_G_field_top_svg = "M 0.24609375,0.24609375 V 237.99805 C 72.097803,221.01187 117.88867,181.80653 190.20508,182.55859 c 53.45966,0.55597 110.0773,40.07557 134.91211,77.15039 33.66598,50.25849 34.62689,81.27887 32.70312,115.42579 -1.92377,34.14691 -29.69983,48.58665 -44.48633,64.6875 -10.8212,11.78308 -22.36429,24.2871 -23.32617,40.1582 -0.68683,11.33273 -4.56071,21.80703 -12.14844,31.77344 H 511.75391 V 0.24609375 Z M 339.30469,12.744141 496.81445,85.0625 V 99.074219 L 374.17383,166.16602 H 362.63086 L 198.62891,88.974609 198.86914,76.228516 Z"
private const val basic_tile_V_G_field_bottom_svg = basic_tile_V_field_bottom_svg
private const val basic_tile_V_G_road_svg = basic_tile_V_road_svg

val V_G = UiTile(
    drawable = Res.drawable.tile_basic_V_G,
    tile = Tiles.Basic.V_G,
    uiElements = mapOf(
        V_G.garden to UiTile.UiElement(
            shape = svgToShape(basic_tile_V_G_garden_svg),
            figurePlacement = Offset(0.7f, 0.17f),
        ),
        V_G.fieldTop to UiTile.UiElement(
            shape = svgToShape(basic_tile_V_G_field_top_svg),
            figurePlacement = Offset(0.2f, 0.2f),
        ),
        V_G.fieldBottom to UiTile.UiElement(
            shape = svgToShape(basic_tile_V_G_field_bottom_svg),
            figurePlacement = Offset(0.3f, 0.7f),
        ),
        V_G.road to UiTile.UiElement(
            shape = svgToShape(basic_tile_V_G_road_svg),
            figurePlacement = Offset(0.65f, 0.6f),
        ),
    ),
)
