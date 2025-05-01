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
interface ElementGroup<P : Position> {

    val value: Set<P>

    fun rotate(rotation: Rotation): ElementGroup<P>

    companion object {

        fun edges(block: Edge.Builder.Companion.() -> Edge.Builder): Edge =
            with(Edge.Builder.Companion, block).build()

        fun field(vararg connectedCities: City, block: Field.Builder.Companion.() -> Field.Builder): Field =
            with(Field.Builder.Companion, block).build(*connectedCities)

        fun road(vararg boons: Boon.Road, block: Road.Builder.Companion.() -> Road.Builder): Road =
            with(Road.Builder.Companion, block).build(boons.toSet())

        fun city(vararg boons: Boon.City, block: City.Builder.Companion.() -> City.Builder): City =
            with(City.Builder.Companion, block).build(boons.toSet())
    }

    data object Center : ElementGroup<Position.Center> {

        override val value = setOf(Position.Center)

        override fun rotate(rotation: Rotation) = this
    }

    @ConsistentCopyVisibility
    data class Edge private constructor(
        override val value: Set<Position.Edge>,
    ) : ElementGroup<Position.Edge> {

        override fun rotate(rotation: Rotation) = Edge(
            value = value.map { it.rotate(rotation) }.toSet(),
        )

        class Builder private constructor(private val value: Set<Position.Edge>) {

            private constructor(value: Position.Edge) : this(setOf(value))

            operator fun plus(other: Builder) = Builder(value + other.value)

            fun build() = Edge(value)

            companion object {
                val top = Builder(Position.Edge.Top)
                val right = Builder(Position.Edge.Right)
                val bottom = Builder(Position.Edge.Bottom)
                val left = Builder(Position.Edge.Left)

                val all = top + right + bottom + left
            }
        }
    }

@ConsistentCopyVisibility
    data class City private constructor(
        override val value: Set<Position.Edge>,
        val boons: Set<Boon.City>,
    ) : ElementGroup<Position.Edge> {

        override fun rotate(rotation: Rotation) = City(
            value = value.map { it.rotate(rotation) }.toSet(),
            boons = boons,
        )

        class Builder private constructor(private val value: Set<Position.Edge>) {

            private constructor(value: Position.Edge) : this(setOf(value))

            operator fun plus(other: Builder) = Builder(value + other.value)

            fun build(boons: Set<Boon.City>) = City(value, boons)

            companion object {
                val top = Builder(Position.Edge.Top)
                val right = Builder(Position.Edge.Right)
                val bottom = Builder(Position.Edge.Bottom)
                val left = Builder(Position.Edge.Left)

                val all = top + right + bottom + left
            }
        }
    }

    @ConsistentCopyVisibility
    data class Road private constructor(
        override val value: Set<Position.Edge>,
        val boons: Set<Boon.Road>,
    ) : ElementGroup<Position.Edge> {

        override fun rotate(rotation: Rotation) = Road(
            value = value.map { it.rotate(rotation) }.toSet(),
            boons = boons,
        )

        class Builder private constructor(private val value: Set<Position.Edge>) {

            private constructor(value: Position.Edge) : this(setOf(value))

            operator fun plus(other: Builder) = Builder(value + other.value)

            fun build(boons: Set<Boon.Road>) = Road(value, boons)

            companion object {
                val top = Builder(Position.Edge.Top)
                val right = Builder(Position.Edge.Right)
                val bottom = Builder(Position.Edge.Bottom)
                val left = Builder(Position.Edge.Left)

                val all = top + right + bottom + left
            }
        }
    }

    @ConsistentCopyVisibility
    data class Field private constructor(
        override val value: Set<Position.SplitEdge>,
        val connectedCities: Set<City>,
    ) : ElementGroup<Position.SplitEdge> {

        override fun rotate(rotation: Rotation) = Field(
            value = value.map { it.rotate(rotation) }.toSet(),
            connectedCities = connectedCities.map { it.rotate(rotation) }.toSet(),
        )

        class Builder private constructor(private val value: Set<Position.SplitEdge>) {

            private constructor(value: Position.SplitEdge) : this(setOf(value))

            operator fun plus(other: Builder) = Builder(value + other.value)

            fun build(vararg connectedCities: City) = Field(value, connectedCities.toSet())

            companion object {
                val none = Builder(emptySet())

                val topLeft = Builder(Position.SplitEdge.TopLeft)
                val topRight = Builder(Position.SplitEdge.TopRight)
                val rightTop = Builder(Position.SplitEdge.RightTop)
                val rightBottom = Builder(Position.SplitEdge.RightBottom)
                val bottomRight = Builder(Position.SplitEdge.BottomRight)
                val bottomLeft = Builder(Position.SplitEdge.BottomLeft)
                val leftBottom = Builder(Position.SplitEdge.LeftBottom)
                val leftTop = Builder(Position.SplitEdge.LeftTop)

                val top = topLeft + topRight
                val right = rightTop + rightBottom
                val bottom = bottomRight + bottomLeft
                val left = leftBottom + leftTop

                val all = top + right + bottom + left
            }
        }
    }
}
