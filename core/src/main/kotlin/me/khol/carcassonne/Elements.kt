package me.khol.carcassonne

interface Elements {

    operator fun <P : Position, G : ElementGroup<P>> get(key: Element<P, G>): List<G>

    fun rotate(rotation: Rotation): Elements
}

interface MutableElements : Elements {

    fun <P : Position, G : ElementGroup<P>> add(key: Element<P, G>, vararg positions: G)
}

fun elements(builder: MutableElements.() -> Unit): Elements = ElementsBuilder(
    mutableMapOf()
).apply(builder)

private data class ElementsBuilder(
    private val map: MutableMap<Element<*, *>, List<ElementGroup<*>>>
) : MutableElements {

    override fun <P : Position, G : ElementGroup<P>> add(key: Element<P, G>, vararg positions: G) {
        map[key] = get(key) + positions.toList()
    }

    override fun <P : Position, G : ElementGroup<P>> get(key: Element<P, G>): List<G> {
        @Suppress("UNCHECKED_CAST")
        return map.getOrDefault(key, emptyList<G>()) as List<G>
    }

    override fun rotate(rotation: Rotation): Elements {
        return map
            .mapValues { it.value.map { it.rotate(rotation) } }
            .toMutableMap()
            .let(::ElementsBuilder)
    }

    override fun toString() = map.toString()
}
