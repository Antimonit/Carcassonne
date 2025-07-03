package me.khol.carcassonne

interface Element<out P : ElementPosition> {

    val positions: Set<P>

    fun rotate(rotation: Rotation): Element<P>

    data object Monastery : Element<ElementPosition.Center> {

        override val positions = setOf(ElementPosition.Center)

        override fun rotate(rotation: Rotation) = this
    }

    data object Garden : Element<ElementPosition.Center> {

        override val positions = setOf(ElementPosition.Center)

        override fun rotate(rotation: Rotation) = this
    }

    data object RiverStart : Element<ElementPosition.Center> {

        override val positions = setOf(ElementPosition.Center)

        override fun rotate(rotation: Rotation) = this
    }

    data object RiverEnd : Element<ElementPosition.Center> {

        override val positions = setOf(ElementPosition.Center)

        override fun rotate(rotation: Rotation) = this
    }

    data object CropCircle : Element<ElementPosition.Center> {

        override val positions = setOf(ElementPosition.Center)

        override fun rotate(rotation: Rotation) = this
    }

    @ConsistentCopyVisibility
    data class River private constructor(
        override val positions: Set<ElementPosition.Edge>,
    ) : Element<ElementPosition.Edge> {

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
    ) : Element<ElementPosition.Edge> {

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
    ) : Element<ElementPosition.Edge> {

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
    ) : Element<ElementPosition.SplitEdge> {

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
