package me.khol.carcassonne

interface ElementGroup<P : ElementPosition> {

    val positions: Set<P>

    fun rotate(rotation: Rotation): ElementGroup<P>

    data object Center : ElementGroup<ElementPosition.Center> {

        override val positions = setOf(ElementPosition.Center)

        override fun rotate(rotation: Rotation) = this
    }

    data object Monastery : ElementGroup<ElementPosition.Center> {

        override val positions = setOf(ElementPosition.Center)

        override fun rotate(rotation: Rotation) = this
    }

    data object Garden : ElementGroup<ElementPosition.Center> {

        override val positions = setOf(ElementPosition.Center)

        override fun rotate(rotation: Rotation) = this
    }

    @ConsistentCopyVisibility
    data class River private constructor(
        override val positions: Set<ElementPosition.Edge>,
    ) : ElementGroup<ElementPosition.Edge> {

        constructor(
            block: EdgeBuilder,
        ) : this(ElementPosition.Edge.builder(block))

        override fun rotate(rotation: Rotation) = River(
            positions = positions.map { it.rotate(rotation) }.toSet(),
        )
    }

    @ConsistentCopyVisibility
    data class City private constructor(
        override val positions: Set<ElementPosition.Edge>,
        val boons: Set<Boon.City>,
    ) : ElementGroup<ElementPosition.Edge> {

        constructor(
            vararg boons: Boon.City,
            block: EdgeBuilder,
        ) : this(ElementPosition.Edge.builder(block), boons.toSet())

        override fun rotate(rotation: Rotation) = City(
            positions = positions.map { it.rotate(rotation) }.toSet(),
            boons = boons,
        )
    }

    @ConsistentCopyVisibility
    data class Road private constructor(
        override val positions: Set<ElementPosition.Edge>,
        val boons: Set<Boon.Road>,
    ) : ElementGroup<ElementPosition.Edge> {

        constructor(
            vararg boons: Boon.Road,
            block: EdgeBuilder,
        ) : this(ElementPosition.Edge.builder(block), boons.toSet())

        override fun rotate(rotation: Rotation) = Road(
            positions = positions.map { it.rotate(rotation) }.toSet(),
            boons = boons,
        )
    }

    @ConsistentCopyVisibility
    data class Field private constructor(
        override val positions: Set<ElementPosition.SplitEdge>,
        val connectedCities: Set<City>,
    ) : ElementGroup<ElementPosition.SplitEdge> {

        constructor(
            vararg connectedCities: City,
            block: SplitEdgeBuilder,
        ) : this(ElementPosition.SplitEdge.builder(block), connectedCities.toSet())

        override fun rotate(rotation: Rotation) = Field(
            positions = positions.map { it.rotate(rotation) }.toSet(),
            connectedCities = connectedCities.map { it.rotate(rotation) }.toSet(),
        )
    }
}
