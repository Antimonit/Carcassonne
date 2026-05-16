package me.khol.carcassonne.ui.tile.basic

import androidx.compose.ui.geometry.Offset
import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.tile_basic_B
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.svgToShape

private const val basic_tile_B_field_svg = "M 0.1171875,0.1171875 V 511.88281 H 511.88281 V 0.1171875 Z M 237.82617,60.298828 h 3.12695 v 8.609375 h 7.38868 v 3.144531 h -7.38868 V 86.158203 L 271,164.13086 l 13.43555,5.87891 0.0586,1.37109 -5.78711,2.53906 v 44.60742 l 49.83594,22.72461 7.57617,12.26563 56.93164,23.625 v 1.62304 h -5.95312 v 53.625 l -33.00391,18.69727 0.24023,20.74023 c 0,10e-6 -14.18728,10.76249 -27.29296,11.96485 -13.10569,1.20235 -30.29883,-5.41211 -30.29883,-5.41211 l -45.57032,22.12305 -137.30859,-77.43164 v -83.8711 c 0,0 -2.95609,-1.82773 -2.87109,-5.22851 0.085,-3.40077 6.1636,-8.71467 16.06836,-13.94336 9.90476,-5.2287 15.30468,-8.07618 15.30468,-8.07618 l 12.25782,-14.64648 12.74414,-18.51562 12.86523,6.01171 13.70703,7.69532 V 171.9375 l -6.13086,-3.99805 v -1.44336 l 16.77149,-7.57422 33.24609,-72.279292 V 72.052734 h -6.78515 v -3.144531 h 6.78515 z"
private const val basic_tile_B_monastery_svg = "m 237.82617,60.298828 v 8.609375 h -6.78515 v 3.144531 h 6.78515 v 14.589844 l -33.24609,72.279292 -16.77149,7.57422 v 1.44336 l 6.13086,3.99805 v 20.56055 l -13.70703,-7.69532 -12.86523,-6.01171 -12.74414,18.51562 -12.25782,14.64648 c 0,0 -5.39992,2.84748 -15.30468,8.07618 -9.90476,5.22869 -15.98336,10.54259 -16.06836,13.94336 -0.085,3.40078 2.87109,5.22851 2.87109,5.22851 v 83.8711 l 137.30859,77.43164 45.57032,-22.12305 c 0,0 17.19314,6.61446 30.29883,5.41211 13.10568,-1.20236 27.29296,-11.96484 27.29296,-11.96485 l -0.24023,-20.74023 33.00391,-18.69727 v -53.625 h 5.95312 v -1.62304 l -56.93164,-23.625 -7.57617,-12.26563 -49.83594,-22.72461 v -44.60742 l 5.78711,-2.53906 -0.0586,-1.37109 L 271,164.13086 240.95312,86.158203 V 72.052734 h 7.38868 v -3.144531 h -7.38868 v -8.609375 z"

val B = UiTile(
    drawable = Res.drawable.tile_basic_B,
    tile = Tiles.Basic.B.tile,
    uiElements = mapOf(
        Tiles.Basic.B.field to UiTile.UiElement(
            shape = svgToShape(basic_tile_B_field_svg),
            figurePlacement = Offset(0.8f, 0.2f),
        ),
        Tiles.Basic.B.monastery to UiTile.UiElement(
            shape = svgToShape(basic_tile_B_monastery_svg),
            figurePlacement = Offset(0.48f, 0.53f),
        ),
    )
)
