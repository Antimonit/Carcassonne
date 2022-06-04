package me.khol.carcassonne

interface Position {

    object Center : Position {
        override fun toString(): String = javaClass.simpleName
    }

    enum class Edge : Position {
        Top,
        Right,
        Bottom,
        Left,
    }

    enum class SplitEdge : Position {
        TopLeft,
        TopRight,
        RightTop,
        RightBottom,
        BottomRight,
        BottomLeft,
        LeftBottom,
        LeftTop,
    }
}
