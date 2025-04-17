package me.khol.carcassonne.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

data class Tile(
    val edges: Edges,
) {

    enum class Edge {
        Field,
        Road,
        City,
        River,
    }

    data class Edges(
        val top: Edge,
        val right: Edge,
        val bottom: Edge,
        val left: Edge,
    )
}

data class PlacedTile(
    val x: Int,
    val y: Int,
    val tile: Tile,
)

private val tileSize = 128.dp

@Composable
fun Tile(
    placedTile: PlacedTile,
    modifier: Modifier = Modifier,
) {
    TileSurface(
        modifier = modifier,
    ) {
        TileContents(placedTile)
    }
}

@Composable
private fun TileSurface(
    modifier: Modifier = Modifier,
    contents: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val elevation by animateDpAsState(if (isHovered) 4.dp else 1.dp)
    val scale by animateFloatAsState(if (isHovered) 1.1f else 1f)

    Surface(
        modifier = modifier
            .zIndexOnHover()
            .size(tileSize)
            .padding(2.dp)
            .hoverable(interactionSource = interactionSource)
            .scale(scale),
        elevation = elevation,
        shape = RoundedCornerShape(4.dp),
    ) {
        contents()
    }
}

@Composable
private fun TileContents(
    placedTile: PlacedTile,
) {
    val imageVector = remember { tileImageVector() }
    Image(imageVector, null, Modifier.size(tileSize))

    val strokeWidth = with(LocalDensity.current) { 4.dp.toPx() }

    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            this.drawArc(
                brush = SolidColor(Color.Black),
                startAngle = 0f,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(width = strokeWidth),
                topLeft = Offset(-size.width / 2, -size.height / 2),
                size = size,
            )
            drawLine(
                brush = SolidColor(Color.Black),
                start = Offset(0f, 0f),
                end = Offset(64f, 64f),
                strokeWidth = strokeWidth,
//                pathEffect = PathEffect.stampedPathEffect()
            )
        }
    )

    Box(
        modifier = Modifier
    ) {
        @Composable
        fun Circle(x: Int, y: Int, color: Color) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = tileSize * x / 2, y = tileSize * y / 2)
                    .clip(CircleShape)
                    .size(32.dp)
                    .background(color)
            )
        }

        val tile = remember { placedTile.tile }
        Circle(-1, 0, tile.edges.left.color)
        Circle(1, 0, tile.edges.right.color)
        Circle(0, -1, tile.edges.top.color)
        Circle(0, 1, tile.edges.bottom.color)
        Text(
            text = "[${placedTile.x}, ${placedTile.y}]",
            modifier = Modifier.align(Alignment.Center),
        )
    }

//    val interactionSource = remember { MutableInteractionSource() }
//    val isHovered by interactionSource.collectIsHoveredAsState()
//    val color by animateColorAsState(if (isHovered) Color.Magenta else Color.Cyan)
//    val shape = DifferenceShape()
//    Box(
//        modifier = Modifier
//            .background(color = color, shape = shape)
//            .clip(shape)
//            .border(2.dp, Color.Green)
//            .hoverable(interactionSource),
//    )
}

class DifferenceShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val offset = Offset(x = size.width, y = size.height)
        val path1 = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        offset = offset / 8f,
                        size = size / 2f,
                    ),
                )
            )
        }
        val path2 = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        offset = offset / 4f,
                        size = size / 2f
                    ),
                )
            )
        }
        return Outline.Generic(
            path = Path.combine(
                operation = PathOperation.Difference,
                path1 = path1,
                path2 = path2,
            )
        )
    }
}

val Tile.Edge.color: Color
    get() = when (this) {
        Tile.Edge.Field -> colorField
        Tile.Edge.Road -> colorRoad
        Tile.Edge.City -> colorCity
        Tile.Edge.River -> colorRiver
    }

val colorField = Color(0.2f, 0.8f, 0.2f)
val colorRoad = Color(0.9f, 0.9f, 0.9f)
val colorCity = Color(0.9f, 0.5f, 0.5f)
val colorRiver = Color(0.2f, 0.2f, 0.8f)

