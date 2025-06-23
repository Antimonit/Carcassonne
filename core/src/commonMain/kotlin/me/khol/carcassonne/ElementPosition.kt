package me.khol.carcassonne

typealias EdgeBuilder = ElementPosition.Edge.Builder.Companion.() -> ElementPosition.Edge.Builder
typealias SplitEdgeBuilder = ElementPosition.SplitEdge.Builder.Companion.() -> ElementPosition.SplitEdge.Builder

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

        class Builder private constructor(private val value: Set<Edge>) {

            private constructor(value: Edge) : this(setOf(value))

            operator fun plus(other: Builder) = Builder(value + other.value)

            fun build() = value

            companion object {
                val top = Builder(Top)
                val right = Builder(Right)
                val bottom = Builder(Bottom)
                val left = Builder(Left)

                val all = top + right + bottom + left
            }
        }

        companion object {
            private val all = listOf(Top, Right, Bottom, Left)

            fun builder(block: EdgeBuilder): Set<Edge> =
                Builder.Companion.run(block).build()
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

        class Builder private constructor(private val value: Set<SplitEdge>) {

            private constructor(value: SplitEdge) : this(setOf(value))

            operator fun plus(other: Builder) = Builder(value + other.value)

            fun build() = value

            companion object {
                val none = Builder(emptySet())

                val topLeft = Builder(TopLeft)
                val topRight = Builder(TopRight)
                val rightTop = Builder(RightTop)
                val rightBottom = Builder(RightBottom)
                val bottomRight = Builder(BottomRight)
                val bottomLeft = Builder(BottomLeft)
                val leftBottom = Builder(LeftBottom)
                val leftTop = Builder(LeftTop)

                val top = topLeft + topRight
                val right = rightTop + rightBottom
                val bottom = bottomRight + bottomLeft
                val left = leftBottom + leftTop

                val all = top + right + bottom + left
            }
        }

        companion object {
            private val all = listOf(TopLeft, TopRight, RightTop, RightBottom, BottomRight, BottomLeft, LeftBottom, LeftTop)

            fun builder(block: SplitEdgeBuilder): Set<SplitEdge> =
                Builder.Companion.run(block).build()
        }

        fun rotate(rotation: Rotation): SplitEdge =
            all[(all.indexOf(this) + rotation.ordinal * 2) % all.size]
    }
}

fun ElementPosition.Edge.oppositeEdge(): ElementPosition.Edge =
    when (this) {
        ElementPosition.Edge.Top -> ElementPosition.Edge.Bottom
        ElementPosition.Edge.Right -> ElementPosition.Edge.Left
        ElementPosition.Edge.Bottom -> ElementPosition.Edge.Top
        ElementPosition.Edge.Left -> ElementPosition.Edge.Right
    }

fun ElementPosition.SplitEdge.oppositeEdge(): ElementPosition.SplitEdge =
    when (this) {
        ElementPosition.SplitEdge.TopLeft -> ElementPosition.SplitEdge.BottomLeft
        ElementPosition.SplitEdge.TopRight -> ElementPosition.SplitEdge.BottomRight
        ElementPosition.SplitEdge.RightTop -> ElementPosition.SplitEdge.LeftTop
        ElementPosition.SplitEdge.RightBottom -> ElementPosition.SplitEdge.LeftBottom
        ElementPosition.SplitEdge.BottomLeft -> ElementPosition.SplitEdge.TopLeft
        ElementPosition.SplitEdge.BottomRight -> ElementPosition.SplitEdge.TopRight
        ElementPosition.SplitEdge.LeftTop -> ElementPosition.SplitEdge.RightTop
        ElementPosition.SplitEdge.LeftBottom -> ElementPosition.SplitEdge.RightBottom
    }
