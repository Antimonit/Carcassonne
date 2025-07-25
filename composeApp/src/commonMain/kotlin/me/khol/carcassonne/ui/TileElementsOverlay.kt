package me.khol.carcassonne.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import me.khol.carcassonne.Element
import me.khol.carcassonne.ui.tile.UiTile
import me.khol.carcassonne.ui.tile.UiTiles
import me.khol.carcassonne.ui.tile.tileSize
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TileElementsOverlay(
    onElementClick: (Element<*>) -> Unit,
    uiTile: UiTile,
) {
    uiTile.uiElements.forEach { (element, uiElement) ->
        val shape = uiElement.shape
        val interactionSource = remember { MutableInteractionSource() }
        val hovered by interactionSource.collectIsHoveredAsState()

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
                    onElementClick(element)
                }
                .hoverable(interactionSource = interactionSource)
        )
    }
}

@Preview
@Composable
fun TileElementsOverlayPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .size(tileSize)
        ) {
            TileElementsOverlay(
                onElementClick = {},
                uiTile = UiTiles.Basic.A,
            )
        }
    }
}
