package me.khol.carcassonne.common

import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.EmptyPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kotlin.random.Random

fun tileImageVector(): ImageVector {
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