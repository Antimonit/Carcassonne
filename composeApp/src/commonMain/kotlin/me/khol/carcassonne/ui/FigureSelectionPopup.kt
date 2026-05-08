package me.khol.carcassonne.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirst
import me.khol.carcassonne.Element
import me.khol.carcassonne.Game
import me.khol.carcassonne.Phase
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.PlacedTile
import me.khol.carcassonne.PlayerFigure
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.figure.Abbot
import me.khol.carcassonne.fixtures.Players
import me.khol.carcassonne.placed
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basicTileset
import me.khol.carcassonne.validFigurePlacements
import org.jetbrains.compose.resources.imageResource

@Composable
fun FigureSelectionPopupBox(
    phase: Phase.PlacingFigure,
    onPlaceFigure: (Phase.PlacingFigure, PlacedFigure) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Layout(
        {
            Box(
                modifier = Modifier.layoutId("anchor"),
                contentAlignment = Alignment.Center,
                content = content,
            )
            Box(
                modifier = Modifier.layoutId("popup"),
                content = {
                    FigureSelectionPopup(
                        phase = phase,
                        onPlaceFigure = onPlaceFigure,
                    )
                },
            )
        },
        modifier = modifier,
    ) { measurables, constraints ->
        // Measure with empty constraints so that the popup does not affect the size of the `content`.
        // This makes the Popup appear "floating".
        val popupPlaceable = measurables.fastFirst { it.layoutId == "popup" }.measure(Constraints())
        val anchorPlaceable = measurables.fastFirst { it.layoutId == "anchor" }.measure(constraints)

        layout(
            width = anchorPlaceable.width,
            height = anchorPlaceable.height,
        ) {
            anchorPlaceable.placeRelative(0, 0)
            popupPlaceable.placeRelative(
                x = ((anchorPlaceable.width - popupPlaceable.width) / 2),
                y = (-popupPlaceable.height),
            )
        }
    }
}

@Composable
fun FigureSelectionPopup(
    phase: Phase.PlacingFigure,
    onPlaceFigure: (Phase.PlacingFigure, PlacedFigure) -> Unit,
    modifier: Modifier = Modifier,
) {
    val placedFigure = phase.placedFigure
    val alternativeFigures = if (placedFigure != null) {
        phase.validFigurePlacements.getValue(placedFigure.placedElement.rotatedElement)
    } else {
        emptyList()
    }

    AnimatedContent(
        targetState = alternativeFigures,
        contentAlignment = Alignment.BottomCenter,
        transitionSpec = {
            fadeIn(
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
            ) + scaleIn(
                initialScale = 0.5f,
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                transformOrigin = TransformOrigin(0.5f, 1f),
            ) togetherWith fadeOut(
                animationSpec = spring(stiffness = Spring.StiffnessMedium),
            ) + scaleOut(
                targetScale = 0.5f,
                animationSpec = spring(stiffness = Spring.StiffnessMedium),
                transformOrigin = TransformOrigin(0.5f, 1f),
            )
        },
        modifier = modifier
    ) { figures ->
        if (figures.size >= 2) {
            Surface(
                shadowElevation = 4.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    // Apply padding to the contents rather than the AnimatedContent
                    // so that the shadow is not clipped during the transition.
                    .padding(8.dp)
                    .height(48.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .height(48.dp)
                        .padding(4.dp)
                ) {
                    figures.forEach { figure ->
                        Surface(
                            onClick = { onPlaceFigure(phase, figure) },
                            shape = RoundedCornerShape(4.dp),
                            color = if (figure == placedFigure) {
                                Color.Black.copy(alpha = 0.06f)
                            } else {
                                Color.Transparent
                            },
                            modifier = Modifier
                                .size(40.dp)
                        ) {
                            Image(
                                bitmap = imageResource(
                                    figure.figure.figure.drawable(
                                        player = figure.figure.player,
                                        isField = figure.placedElement.rotatedElement.element is Element.Field,
                                    )
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FigureSelectionPopupPreview() {
    fun Game.placingFigure(placedTile: PlacedTile, placedFigure: PlacedFigure? = null) =
        Phase.PlacingFigure(
            placedTile = placedTile,
            validTilePlacements = board.possibleSpacesForTile(placedTile.rotatedTile.tile),
            validFigurePlacements = board.validFigurePlacements(
                placedTile = placedTile,
                currentPlayer = currentPlayer,
                figureSupply = figureSupply,
            ),
            placedFigure = placedFigure,
        )

    MaterialTheme {
        Surface {
            val game = Game.new(
                tilesets = listOf(basicTileset),
                startingTile = Tiles.Basic.D.tile,
                players = listOf(Players.green),
            )
            val placedTile = Tiles.Basic.A.tile.rotated(Rotation.ROTATE_90).placed(1, 0)
            val placedFigure = PlacedFigure(
                placedElement = Tiles.Basic.A.monastery.rotated(Rotation.ROTATE_90).placed(1, 0),
                figure = PlayerFigure(figure = Abbot, player = Players.green),
            )
            FigureSelectionPopupBox(
                phase = game.placingFigure(placedTile, placedFigure),
                onPlaceFigure = { _, _ -> },
                modifier = Modifier
                    .padding(64.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(112.dp)
                        .background(Color.Blue)
                )
            }
        }
    }
}
