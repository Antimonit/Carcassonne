package me.khol.carcassonne.ui.tile.basic

import androidx.compose.ui.geometry.Offset
import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.tile_basic_N_G
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basic.N_G
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.svgToShape

private const val basic_tile_N_G_garden_svg = basic_tile_M_G_garden_svg
private const val basic_tile_N_G_city_svg = basic_tile_M_G_city_svg
private const val basic_tile_N_G_field_svg = basic_tile_M_G_field_svg

val N_G = UiTile(
    drawable = Res.drawable.tile_basic_N_G,
    tile = Tiles.Basic.N_G,
    uiElements = mapOf(
        N_G.garden to UiTile.UiElement(
            shape = svgToShape(basic_tile_N_G_garden_svg),
            figurePlacement = Offset(0.33f, 0.77f),
        ),
        N_G.city to UiTile.UiElement(
            shape = svgToShape(basic_tile_N_G_city_svg),
            figurePlacement = Offset(0.75f, 0.25f),
        ),
        N_G.field to UiTile.UiElement(
            shape = svgToShape(basic_tile_N_G_field_svg),
            figurePlacement = Offset(0.15f, 0.5f),
        ),
    ),
)
