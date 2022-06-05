package me.khol.carcassonne

/**
 * We could define the interface with self-referencing generic type like:
 *
 * ```
 * interface Position<T : Element.Position, P : Position<T, P>>
 * ```
 *
 * which would allow us to define `plus` method as:
 *
 * ```
 * operator fun plus(other: P): P
 *
 * override operator fun plus(other: Center): Center
 * override operator fun plus(other: Edge): Edge
 * override operator fun plus(other: SplitEdge): SplitEdge
 * ```
 *
 * Although this is pretty cool, and it works, it's an overkill since it makes
 * working with this interface much more verbose for virtually no observable
 * benefits. There are no use cases for polymorphic [plus] method anyway.
 *
 *
 * Another less type-safe option would be to use typealiases such as:
 *
 * ```
 * typealias PositionCenters = Set<Element.Position.Center>
 * typealias PositionEdges = Set<Element.Position.Edge>
 * typealias PositionSplitEdges = Set<Element.Position.SplitEdge>
 * ```
 *
 * This approach might be more comfortable in some situations but is less type-safe.
 */
interface Positions<T : Position> {

    val value: Set<T>

    companion object {

        fun edges(block: Edge.Companion.() -> Edge): Edge = with(Edge, block)

        fun splitEdges(block: SplitEdge.Companion.() -> SplitEdge): SplitEdge = with(SplitEdge, block)
    }

    object Center : Positions<Position.Center> {

        override val value = setOf(Position.Center)
    }

    class Edge private constructor(
        override val value: Set<Position.Edge>,
    ) : Positions<Position.Edge> {

        private constructor(value: Position.Edge) : this(setOf(value))

        operator fun plus(
            other: Positions<Position.Edge>,
        ) = Edge(value + other.value)

        companion object {
            val Top = Edge(Position.Edge.Top)
            val Right = Edge(Position.Edge.Right)
            val Bottom = Edge(Position.Edge.Bottom)
            val Left = Edge(Position.Edge.Left)

            val All = Top + Right + Bottom + Left
        }
    }

    class SplitEdge private constructor(
        override val value: Set<Position.SplitEdge>,
    ) : Positions<Position.SplitEdge> {

        private constructor(value: Position.SplitEdge) : this(setOf(value))

        operator fun plus(
            other: Positions<Position.SplitEdge>,
        ) = SplitEdge(value + other.value)

        companion object {
            val TopLeft = SplitEdge(Position.SplitEdge.TopLeft)
            val TopRight = SplitEdge(Position.SplitEdge.TopRight)
            val RightTop = SplitEdge(Position.SplitEdge.RightTop)
            val RightBottom = SplitEdge(Position.SplitEdge.RightBottom)
            val BottomRight = SplitEdge(Position.SplitEdge.BottomRight)
            val BottomLeft = SplitEdge(Position.SplitEdge.BottomLeft)
            val LeftBottom = SplitEdge(Position.SplitEdge.LeftBottom)
            val LeftTop = SplitEdge(Position.SplitEdge.LeftTop)

            val Top = TopLeft + TopRight
            val Right = RightTop + RightBottom
            val Bottom = BottomRight + BottomLeft
            val Left = LeftBottom + LeftTop

            val All = Top + Right + Bottom + Left
        }
    }
}
