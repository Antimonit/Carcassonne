package me.khol.carcassonne.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.EmptyPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.ui.tile.TileSurface
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

@Preview
@Composable
fun VectorTile() {
    TileSurface {
        val imageVector = remember { tileImageVector() }
        Image(imageVector, null, Modifier.size(128.dp))
    }
}

private fun tileImageVector(): ImageVector {
    // TODO Do not use SVG, instead use actual @Composable elements using Outline

    fun PathBuilder.bottomRightRoadPath(move: Boolean = true) {
        if (move)
            moveTo(0.5f, 1f)
        else
            lineTo(0.5f, 1f)
        quadTo(0.5f, 0.5f, 1f, 0.5f)
    }

    fun PathBuilder.topLeftRoadPath(move: Boolean = true) {
        if (move)
            moveTo(0.5f, 0.0f)
        else
            lineTo(0.5f, 0.0f)
        quadTo(0.5f, 0.5f, 0.0f, 0.5f)
    }

    fun ImageVector.Builder.addClippedField(
        fieldId: Int,
        clipPathData: List<PathNode> = EmptyPath,
    ) {
        group(
            name = "field_$fieldId",
            clipPathData = clipPathData
        ) {
            addPath(
                pathData = PathData {
                    moveTo(0f, 0f)
                    horizontalLineTo(1f)
                    verticalLineTo(1f)
                    horizontalLineTo(0f)
                    close()
                },
                fill = SolidColor(colorField.copy(green = colorField.green * Random.nextFloat() / 4 + 0.5f)),
            )
        }
    }

    return ImageVector.Builder(
        name = "Programmatically built image vector",
        defaultWidth = 128.dp,
        defaultHeight = 128.dp,
        viewportWidth = 1f,
        viewportHeight = 1f,
    ).group {

        addClippedField(
            fieldId = 1,
            clipPathData = PathData {
                topLeftRoadPath(true)
                verticalLineTo(0f)
                close()
            }
        )
        addClippedField(
            fieldId = 2,
            clipPathData = PathData {
                bottomRightRoadPath(true)
                verticalLineTo(0f)
                topLeftRoadPath(false)
                verticalLineTo(1f)
                close()
            }
        )
        addClippedField(
            fieldId = 3,
            clipPathData = PathData {
                bottomRightRoadPath(true)
                verticalLineTo(1f)
                close()
            }
        )


        path(
            stroke = SolidColor(colorRoad),
            strokeLineWidth = 0.05f,
        ) {
            bottomRightRoadPath()
        }

        path(
            stroke = SolidColor(colorRoad),
            strokeLineWidth = 0.05f,
        ) {
            topLeftRoadPath()
        }
    }.build()
}

private val colorField = Color(0.2f, 0.8f, 0.2f)
private val colorRoad = Color(0.9f, 0.9f, 0.9f)
private val colorCity = Color(0.9f, 0.5f, 0.5f)
private val colorRiver = Color(0.2f, 0.2f, 0.8f)
