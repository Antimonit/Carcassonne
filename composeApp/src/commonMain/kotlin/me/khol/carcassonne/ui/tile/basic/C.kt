package me.khol.carcassonne.ui.tile.basic

import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.tile_basic_C
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basic.C
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.svgToShape

private const val basic_tile_C_city_svg = "M 0,0 V 512 H 512 V 0 Z"

val C = UiTile(
    drawable = Res.drawable.tile_basic_C,
    tile = Tiles.Basic.C,
    shapes = mapOf(
        svgToShape(basic_tile_C_city_svg) to C.city,
    ),
)
