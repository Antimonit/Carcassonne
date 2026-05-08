package me.khol.carcassonne.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.*
import me.khol.carcassonne.Element
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.Player
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.figure.Abbot
import me.khol.carcassonne.figure.Builder
import me.khol.carcassonne.figure.Figure
import me.khol.carcassonne.figure.Meeple
import me.khol.carcassonne.figure.Pig
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.ui.tile.RotatedUiTile
import me.khol.carcassonne.ui.tile.tileSize
import me.khol.carcassonne.ui.tile.toUiTile
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource

@Composable
fun TileFiguresOverlay(
    figures: List<PlacedFigure>,
    rotatedUiTile: RotatedUiTile,
) {
    rotatedUiTile.rotatedUiElements.forEach { (rotatedElement, uiElement) ->
        val figurePlacement = uiElement.figurePlacement

        val figure: PlacedFigure? = figures.find { it.placedElement.rotatedElement == rotatedElement }
        if (figure != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    bitmap = imageResource(
                        figure.figure.figure.drawable(
                            player = figure.figure.player,
                            isField = figure.placedElement.rotatedElement.element is Element.Field,
                        )
                    ),
                    contentDescription = null,
                    filterQuality = FilterQuality.High,
                    modifier = Modifier
                        .zIndex(1f + figurePlacement.y)
                        .size(40.dp)
                        .align(Alignment.Center)
                        .offset(tileSize * (figurePlacement.x - 0.5f), tileSize * (figurePlacement.y - 0.5f))
                        .rotate(-rotatedUiTile.rotation.degrees)
                )
            }
        }
    }
}

fun Figure.drawable(
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

@Preview
@Composable
private fun TileFiguresOverlayPreview() {
    MaterialTheme {
        Surface {
            val rotatedUiTile = Tiles.Basic.A.tile.rotated(Rotation.ROTATE_90).toUiTile()
            RotatedTile(
                rotatedUiTile = rotatedUiTile,
                overlay = {
                    TileFiguresOverlay(
                        figures = listOf(
                            PlacedFigure(
                                placedElement = Tiles.Basic.A.field.rotated(Rotation.ROTATE_90).placed(0, 0),
                                figure = PlayerFigures.greenMeeple,
                            )
                        ),
                        rotatedUiTile = rotatedUiTile,
                    )
                },
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}
