package me.khol.carcassonne.ui.tile.basic

import androidx.compose.ui.geometry.Offset
import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.tile_basic_G
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basic.G
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.svgToShape

private const val basic_tile_G_city_svg = basic_tile_F_city_svg
private const val basic_tile_G_field_top_svg = basic_tile_F_field_top_svg
private const val basic_tile_G_field_bottom_svg = basic_tile_F_field_bottom_svg

val G = UiTile(
    drawable = Res.drawable.tile_basic_G,
    tile = Tiles.Basic.G,
    uiElements = mapOf(
        G.city to UiTile.UiElement(
            shape = svgToShape(basic_tile_G_city_svg),
            figurePlacement = Offset(0.5f, 0.55f),
        ),
        G.fieldTop to UiTile.UiElement(
            shape = svgToShape(basic_tile_G_field_top_svg),
            figurePlacement = Offset(0.5f, 0.15f),
        ),
        G.fieldBottom to UiTile.UiElement(
            shape = svgToShape(basic_tile_G_field_bottom_svg),
            figurePlacement = Offset(0.5f, 0.85f),
        ),
    ),
)
