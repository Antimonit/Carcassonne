package me.khol.carcassonne.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.figure_farmer_red
import carcassonne.composeapp.generated.resources.figure_meeple_red
import me.khol.carcassonne.Element
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.tileSize
import org.jetbrains.compose.resources.imageResource

@Composable
fun TileFiguresOverlay(
    figures: Set<Element<*>>,
    rotation: Rotation,
    uiTile: UiTile,
) {
    uiTile.uiElements
        .filterKeys { it in figures }
        .forEach { (element, uiElement) ->
            val figurePlacement = uiElement.figurePlacement

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    bitmap = if (element is Element.Field) {
                        imageResource(Res.drawable.figure_farmer_red)
                    } else {
                        imageResource(Res.drawable.figure_meeple_red)
                    },
                    contentDescription = null,
                    filterQuality = FilterQuality.High,
                    modifier = Modifier
                        .zIndex(1f + figurePlacement.y)
                        .size(40.dp)
                        .align(Alignment.Center)
                        .offset(tileSize * (figurePlacement.x - 0.5f), tileSize * (figurePlacement.y - 0.5f))
                        .rotate(
                            degrees = when (rotation) {
                                Rotation.ROTATE_0 -> 0f
                                Rotation.ROTATE_90 -> -90f
                                Rotation.ROTATE_180 -> -180f
                                Rotation.ROTATE_270 -> -270f
                            }
                        )
                )
            }
        }
}
