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

fun Position.Edge.rotate(rotation: Rotation): Position.Edge =
    Position.Edge.entries[(ordinal + rotation.ordinal) % Position.Edge.entries.size]

fun Position.SplitEdge.rotate(rotation: Rotation): Position.SplitEdge =
    Position.SplitEdge.entries[(ordinal + rotation.ordinal * 2) % Position.SplitEdge.entries.size]
