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

        fun edges(block: EdgeBuilder): Edge =
            Edge(ElementPosition.Edge.builder(block))

        fun field(vararg connectedCities: City, block: SplitEdgeBuilder): Field =
            Field(ElementPosition.SplitEdge.builder(block), connectedCities.toSet())

        fun road(vararg boons: Boon.Road, block: EdgeBuilder): Road =
            Road(ElementPosition.Edge.builder(block), boons.toSet())

        fun city(vararg boons: Boon.City, block: EdgeBuilder): City =
            City(ElementPosition.Edge.builder(block), boons.toSet())
    }

    data object Center : ElementGroup<ElementPosition.Center> {

        override val positions = setOf(ElementPosition.Center)

        override fun rotate(rotation: Rotation) = this
    }

    data class Edge(
        override val positions: Set<ElementPosition.Edge>,
    ) : ElementGroup<ElementPosition.Edge> {

        override fun rotate(rotation: Rotation) = Edge(
            positions = positions.map { it.rotate(rotation) }.toSet(),
        )
    }

    data class City(
        override val positions: Set<ElementPosition.Edge>,
        val boons: Set<Boon.City>,
    ) : ElementGroup<ElementPosition.Edge> {

        override fun rotate(rotation: Rotation) = City(
            positions = positions.map { it.rotate(rotation) }.toSet(),
            boons = boons,
        )
    }

    data class Road(
        override val positions: Set<ElementPosition.Edge>,
        val boons: Set<Boon.Road>,
    ) : ElementGroup<ElementPosition.Edge> {

        override fun rotate(rotation: Rotation) = Road(
            positions = positions.map { it.rotate(rotation) }.toSet(),
            boons = boons,
        )
    }

    data class Field(
        override val positions: Set<ElementPosition.SplitEdge>,
        val connectedCities: Set<City>,
    ) : ElementGroup<ElementPosition.SplitEdge> {

        override fun rotate(rotation: Rotation) = Field(
            positions = positions.map { it.rotate(rotation) }.toSet(),
            connectedCities = connectedCities.map { it.rotate(rotation) }.toSet(),
        )
    }
}
