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
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.feature.PlacedElement
import me.khol.carcassonne.feature.PlacedField
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.UiTiles
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TileElementsOverlay(
    onElementClick: (PlacedFigure) -> Unit,
    uiTile: UiTile,
    rotation: Rotation,
    validMeeplePlacements: Map<Element<*>, List<PlacedFigure>>,
) {
    // The UiTile is not rotated but Elements and PlacedFigures are.
    // The whole TileElementsOverlay is visually rotated from the outside.
    uiTile.uiElements
        .mapKeys { validMeeplePlacements.getValue(it.key.rotate(rotation)) }
        .filterKeys { it.isNotEmpty() }
        .forEach { (elements, uiElement) ->
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
                        onElementClick(elements.first())
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
            val uiTile = UiTiles.Basic.A
            val rotation = Rotation.ROTATE_0
            Tile(
                drawable = uiTile.drawable,
                rotation = rotation,
                overlay = {
                    TileElementsOverlay(
                        onElementClick = {},
                        uiTile = uiTile,
                        rotation = rotation,
                        validMeeplePlacements = UiTiles.Basic.A.uiElements.keys.associateWith { emptyList() },
                    )
                    TileFiguresOverlay(
                        figures = listOf(
                            PlacedFigure(
                                placedElement = PlacedField(
                                    coordinates = Coordinates(0, 0),
                                    element = me.khol.carcassonne.tiles.basic.A.field,
                                ),
                                figure = PlayerFigures.greenMeeple,
                            )
                        ),
                        rotation = rotation,
                        uiTile = uiTile,
                    )
                },
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}
