package me.khol.carcassonne

interface Elements {

    operator fun <P : Position, G : ElementGroup<P>> get(key: Element<P, G>): List<G>
}

interface MutableElements : Elements {

    fun <P : Position, G : ElementGroup<P>> add(key: Element<P, G>, vararg positions: G)
}

fun elements(builder: MutableElements.() -> Unit): Elements = ElementsBuilder().apply(builder)

private class ElementsBuilder : MutableElements {

    private val map: MutableMap<Element<*, *>, List<*>> = mutableMapOf()

    override fun <P : Position, G : ElementGroup<P>> add(key: Element<P, G>, vararg positions: G) {
        map[key] = get(key) + positions.toList()
    }

    override fun <P : Position, G : ElementGroup<P>> get(key: Element<P, G>): List<G> {
        @Suppress("UNCHECKED_CAST")
        return map.getOrDefault(key, emptyList<G>()) as List<G>
    }

    override fun toString() = map.toString()
}
