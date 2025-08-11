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
import carcassonne.composeapp.generated.resources.*
import me.khol.carcassonne.Element
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.Player
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.figure.Abbot
import me.khol.carcassonne.figure.Builder
import me.khol.carcassonne.figure.Figure
import me.khol.carcassonne.figure.Meeple
import me.khol.carcassonne.figure.Pig
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.tileSize
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource

@Composable
fun TileFiguresOverlay(
    figures: List<PlacedFigure>,
    rotation: Rotation,
    uiTile: UiTile,
) {
    uiTile.uiElements.forEach { (element, uiElement) ->
        val figurePlacement = uiElement.figurePlacement

        val figure: PlacedFigure? = figures.find { it.placedElement.element == element.rotate(rotation) }
        if (figure != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    bitmap = imageResource(
                        figure.figure.figure.drawable(
                            player = figure.figure.player,
                            isField = figure.placedElement.element is Element.Field,
                        )
                    ),
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
}

private fun Figure.drawable(
    player: Player,
    isField: Boolean,
): DrawableResource = when (this) {
    Meeple -> if (isField) {
        when (player.color) {
            Player.Color.Black -> Res.drawable.figure_farmer_black
            Player.Color.Blue -> Res.drawable.figure_farmer_blue
            Player.Color.Green -> Res.drawable.figure_farmer_green
            Player.Color.Pink -> Res.drawable.figure_farmer_pink
            Player.Color.Red -> Res.drawable.figure_farmer_red
            Player.Color.Yellow -> Res.drawable.figure_farmer_yellow
        }
    } else {
        when (player.color) {
            Player.Color.Black -> Res.drawable.figure_meeple_black
            Player.Color.Blue -> Res.drawable.figure_meeple_blue
            Player.Color.Green -> Res.drawable.figure_meeple_green
            Player.Color.Pink -> Res.drawable.figure_meeple_pink
            Player.Color.Red -> Res.drawable.figure_meeple_red
            Player.Color.Yellow -> Res.drawable.figure_meeple_yellow
        }
    }
    Abbot -> when (player.color) {
        Player.Color.Black -> Res.drawable.figure_abbot_black
        Player.Color.Blue -> Res.drawable.figure_abbot_blue
        Player.Color.Green -> Res.drawable.figure_abbot_green
        Player.Color.Pink -> Res.drawable.figure_abbot_pink
        Player.Color.Red -> Res.drawable.figure_abbot_red
        Player.Color.Yellow -> Res.drawable.figure_abbot_yellow
    }
    Builder -> TODO()
    Pig -> TODO()
    else -> TODO()
}
