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
interface ElementGroup<P : ElementPosition> {

    val positions: Set<P>

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

    data object Center : ElementGroup<ElementPosition.Center> {

        override val positions = setOf(ElementPosition.Center)

        override fun rotate(rotation: Rotation) = this
    }

    @ConsistentCopyVisibility
    data class Edge private constructor(
        override val positions: Set<ElementPosition.Edge>,
    ) : ElementGroup<ElementPosition.Edge> {

        override fun rotate(rotation: Rotation) = Edge(
            positions = positions.map { it.rotate(rotation) }.toSet(),
        )

        class Builder private constructor(private val value: Set<ElementPosition.Edge>) {

            private constructor(value: ElementPosition.Edge) : this(setOf(value))

            operator fun plus(other: Builder) = Builder(value + other.value)

            fun build() = Edge(value)

            companion object {
                val top = Builder(ElementPosition.Edge.Top)
                val right = Builder(ElementPosition.Edge.Right)
                val bottom = Builder(ElementPosition.Edge.Bottom)
                val left = Builder(ElementPosition.Edge.Left)

                val all = top + right + bottom + left
            }
        }
    }

    @ConsistentCopyVisibility
    data class City private constructor(
        override val positions: Set<ElementPosition.Edge>,
        val boons: Set<Boon.City>,
    ) : ElementGroup<ElementPosition.Edge> {

        override fun rotate(rotation: Rotation) = City(
            positions = positions.map { it.rotate(rotation) }.toSet(),
            boons = boons,
        )

        class Builder private constructor(private val value: Set<ElementPosition.Edge>) {

            private constructor(value: ElementPosition.Edge) : this(setOf(value))

            operator fun plus(other: Builder) = Builder(value + other.value)

            fun build(boons: Set<Boon.City>) = City(value, boons)

            companion object {
                val top = Builder(ElementPosition.Edge.Top)
                val right = Builder(ElementPosition.Edge.Right)
                val bottom = Builder(ElementPosition.Edge.Bottom)
                val left = Builder(ElementPosition.Edge.Left)

                val all = top + right + bottom + left
            }
        }
    }

    @ConsistentCopyVisibility
    data class Road private constructor(
        override val positions: Set<ElementPosition.Edge>,
        val boons: Set<Boon.Road>,
    ) : ElementGroup<ElementPosition.Edge> {

        override fun rotate(rotation: Rotation) = Road(
            positions = positions.map { it.rotate(rotation) }.toSet(),
            boons = boons,
        )

        class Builder private constructor(private val value: Set<ElementPosition.Edge>) {

            private constructor(value: ElementPosition.Edge) : this(setOf(value))

            operator fun plus(other: Builder) = Builder(value + other.value)

            fun build(boons: Set<Boon.Road>) = Road(value, boons)

            companion object {
                val top = Builder(ElementPosition.Edge.Top)
                val right = Builder(ElementPosition.Edge.Right)
                val bottom = Builder(ElementPosition.Edge.Bottom)
                val left = Builder(ElementPosition.Edge.Left)

                val all = top + right + bottom + left
            }
        }
    }

    @ConsistentCopyVisibility
    data class Field private constructor(
        override val positions: Set<ElementPosition.SplitEdge>,
        val connectedCities: Set<City>,
    ) : ElementGroup<ElementPosition.SplitEdge> {

        override fun rotate(rotation: Rotation) = Field(
            positions = positions.map { it.rotate(rotation) }.toSet(),
            connectedCities = connectedCities.map { it.rotate(rotation) }.toSet(),
        )

        class Builder private constructor(private val value: Set<ElementPosition.SplitEdge>) {

            private constructor(value: ElementPosition.SplitEdge) : this(setOf(value))

            operator fun plus(other: Builder) = Builder(value + other.value)

            fun build(vararg connectedCities: City) = Field(value, connectedCities.toSet())

            companion object {
                val none = Builder(emptySet())

                val topLeft = Builder(ElementPosition.SplitEdge.TopLeft)
                val topRight = Builder(ElementPosition.SplitEdge.TopRight)
                val rightTop = Builder(ElementPosition.SplitEdge.RightTop)
                val rightBottom = Builder(ElementPosition.SplitEdge.RightBottom)
                val bottomRight = Builder(ElementPosition.SplitEdge.BottomRight)
                val bottomLeft = Builder(ElementPosition.SplitEdge.BottomLeft)
                val leftBottom = Builder(ElementPosition.SplitEdge.LeftBottom)
                val leftTop = Builder(ElementPosition.SplitEdge.LeftTop)

                val top = topLeft + topRight
                val right = rightTop + rightBottom
                val bottom = bottomRight + bottomLeft
                val left = leftBottom + leftTop

                val all = top + right + bottom + left
            }
        }
    }
}
