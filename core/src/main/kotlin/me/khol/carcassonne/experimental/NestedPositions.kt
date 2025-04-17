package me.khol.carcassonne.experimental

sealed interface NestedPositions {

    sealed interface Center : NestedPositions

    sealed interface Edge : NestedPositions

    sealed interface Top : Edge
    sealed interface Right : Edge
    sealed interface Bottom : Edge
    sealed interface Left : Edge

    sealed interface SplitEdge : Edge

    sealed interface TopLeft : SplitEdge, Top
    sealed interface TopRight : SplitEdge, Top
    sealed interface RightTop : SplitEdge, Right
    sealed interface RightBottom : SplitEdge, Right
    sealed interface BottomRight : SplitEdge, Bottom
    sealed interface BottomLeft : SplitEdge, Bottom
    sealed interface LeftBottom : SplitEdge, Left
    sealed interface LeftTop : SplitEdge, Left
}

object PositionsEx {
    object Center : NestedPositions.Center

    object Top : NestedPositions.Top
    object Right : NestedPositions.Right
    object Bottom : NestedPositions.Bottom
    object Left : NestedPositions.Left

    object TopLeft : NestedPositions.TopLeft
    object TopRight : NestedPositions.TopRight
    object RightTop : NestedPositions.RightTop
    object RightBottom : NestedPositions.RightBottom
    object BottomRight : NestedPositions.BottomRight
    object BottomLeft : NestedPositions.BottomLeft
    object LeftBottom : NestedPositions.LeftBottom
    object LeftTop : NestedPositions.LeftTop
}

fun accept(edge: NestedPositions.Edge) = when (edge) {
    PositionsEx.Bottom -> "TODO"
    PositionsEx.Left -> "TODO"
    PositionsEx.Right -> "TODO"
    PositionsEx.Top -> "TODO"
    PositionsEx.BottomLeft -> TODO()
    PositionsEx.BottomRight -> TODO()
    PositionsEx.LeftBottom -> TODO()
    PositionsEx.LeftTop -> TODO()
    PositionsEx.RightBottom -> TODO()
    PositionsEx.RightTop -> TODO()
    PositionsEx.TopLeft -> TODO()
    PositionsEx.TopRight -> TODO()
}

fun accept(splitEdge: NestedPositions.SplitEdge) = when (splitEdge) {
    PositionsEx.BottomLeft -> "TODO"
    PositionsEx.BottomRight -> "TODO"
    PositionsEx.LeftBottom -> "TODO"
    PositionsEx.LeftTop -> "TODO"
    PositionsEx.RightBottom -> "TODO"
    PositionsEx.RightTop -> "TODO"
    PositionsEx.TopLeft -> "TODO"
    PositionsEx.TopRight -> "TODO"
}

fun accept(center: NestedPositions.Center) = when (center) {
    PositionsEx.Center -> "TODO"
}
