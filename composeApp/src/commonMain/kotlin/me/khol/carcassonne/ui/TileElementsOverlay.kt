package me.khol.carcassonne.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.RotatedElement
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.ui.tile.RotatedUiTile
import me.khol.carcassonne.ui.tile.toUiTile

@Composable
fun TileElementsOverlay(
    onElementClick: (PlacedFigure) -> Unit,
    rotatedUiTile: RotatedUiTile,
    validMeeplePlacements: Map<RotatedElement<*>, List<PlacedFigure>>,
) {
    rotatedUiTile.rotatedUiElements
        .mapKeys { validMeeplePlacements.getValue(it.key) }
        .filterKeys { it.isNotEmpty() }
        .forEach { (placedFigures, uiElement) ->
            val shape = uiElement.shape
            val interactionSource = remember { MutableInteractionSource() }
            val hovered by interactionSource.collectIsHoveredAsState()

            // TODO: The player may be able to place more than one type of figure,
            //  typically the small and big meeples.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = shape)
                    .background(
                        brush = if (hovered) {
                            Brush.linearGradient(listOf(Color.Red.copy(alpha = 0.5f), Color.Blue.copy(alpha = 0.5f)))
                        } else {
                            Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
                        },
                        shape = shape,
                    )
                    .clickable {
                        onElementClick(placedFigures.first())
                    }
                    .hoverable(interactionSource = interactionSource)
            )
        }
}

@Preview
@Composable
private fun TileElementsOverlayPreview() {
    MaterialTheme {
        Surface {
            val rotatedUiTile = Tiles.Basic.A.tile.rotated(Rotation.ROTATE_90).toUiTile()
            RotatedTile(
                rotatedUiTile = rotatedUiTile,
                overlay = {
                    TileElementsOverlay(
                        onElementClick = {},
                        rotatedUiTile = rotatedUiTile,
                        validMeeplePlacements = rotatedUiTile.rotatedUiElements.keys.associateWith { emptyList() },
                    )
                    TileFiguresOverlay(
                        figures = listOf(
                            PlacedFigure(
                                placedElement = Tiles.Basic.A.field.rotated(Rotation.ROTATE_0).placed(0, 0),
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
