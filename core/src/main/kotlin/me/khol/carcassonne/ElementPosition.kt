package me.khol.carcassonne

interface ElementPosition {

    /**
     * Elements that do not touch the tile edge.
     * E.g. [ElementKey.Monastery], [ElementKey.Garden].
     */
    data object Center : ElementPosition

    /**
     * Elements that have at most one element per tile edge.
     * E.g. [ElementKey.Road], [ElementKey.City].
     */
    sealed interface Edge : ElementPosition {
        data object Top : Edge
        data object Right : Edge
        data object Bottom : Edge
        data object Left : Edge

        companion object {
            private val all = listOf(Top, Right, Bottom, Left)
        }

        fun rotate(rotation: Rotation): Edge =
            all[(all.indexOf(this) + rotation.ordinal) % all.size]
    }

    /**
     * Elements that have up to two separate elements per tile edge.
     * E.g. [ElementKey.Field].
     */
    sealed interface SplitEdge : ElementPosition {
        data object TopLeft : SplitEdge
        data object TopRight : SplitEdge
        data object RightTop : SplitEdge
        data object RightBottom : SplitEdge
        data object BottomRight : SplitEdge
        data object BottomLeft : SplitEdge
        data object LeftBottom : SplitEdge
        data object LeftTop : SplitEdge

        companion object {
            private val all = listOf(TopLeft, TopRight, RightTop, RightBottom, BottomRight, BottomLeft, LeftBottom, LeftTop)
        }

        fun rotate(rotation: Rotation): SplitEdge =
            all[(all.indexOf(this) + rotation.ordinal * 2) % all.size]
    }
}

fun Position.Edge.oppositeEdge(): Position.Edge =
    when (this) {
        Position.Edge.Top -> Position.Edge.Bottom
        Position.Edge.Right -> Position.Edge.Left
        Position.Edge.Bottom -> Position.Edge.Top
        Position.Edge.Left -> Position.Edge.Right
    }

fun Position.SplitEdge.oppositeEdge(): Position.SplitEdge =
    when (this) {
        Position.SplitEdge.TopLeft -> Position.SplitEdge.BottomLeft
        Position.SplitEdge.TopRight -> Position.SplitEdge.BottomRight
        Position.SplitEdge.RightTop -> Position.SplitEdge.LeftTop
        Position.SplitEdge.RightBottom -> Position.SplitEdge.LeftBottom
        Position.SplitEdge.BottomLeft -> Position.SplitEdge.TopLeft
        Position.SplitEdge.BottomRight -> Position.SplitEdge.TopRight
        Position.SplitEdge.LeftTop -> Position.SplitEdge.RightTop
        Position.SplitEdge.LeftBottom -> Position.SplitEdge.RightBottom
    }
