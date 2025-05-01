package me.khol.carcassonne

interface Position {

    fun rotate(rotation: Rotation): Position

    /**
     * Elements that do not touch the tile edge.
     * E.g. [Element.Monastery], [Element.Garden].
     */
    data object Center : Position {

        override fun rotate(rotation: Rotation): Center =
            this
    }

    /**
     * Elements that have at most one element per tile edge.
     * E.g. [Element.Road], [Element.City].
     */
    sealed interface Edge : Position {
        data object Top : Edge
        data object Right : Edge
        data object Bottom : Edge
        data object Left : Edge

        companion object {
            private val all = listOf(Top, Right, Bottom, Left)
        }

        override fun rotate(rotation: Rotation): Edge =
            all[(all.indexOf(this) + rotation.ordinal) % all.size]
    }

    /**
     * Elements that have up to two separate elements per tile edge.
     * E.g. [Element.Field].
     */
    sealed interface SplitEdge : Position {
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

        override fun rotate(rotation: Rotation): SplitEdge =
            all[(all.indexOf(this) + rotation.ordinal * 2) % all.size]
    }
}
