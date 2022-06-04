package me.khol.carcassonne

sealed interface Elements {

    operator fun <T : Element.Position> get(key: Element<T>): List<Set<T>>
}

sealed interface MutableElements : Elements {

    fun <T : Element.Position> add(key: Element<T>, vararg positions: Set<T>)
}

fun elements(builder: MutableElements.() -> Unit): Elements = ElementsBuilder().apply(builder)

private class ElementsBuilder : MutableElements {

    private val map = mutableMapOf<Element<*>, List<*>>().withDefault { emptyList<Any>() }

    override fun <T : Element.Position> add(key: Element<T>, vararg positions: Set<T>) {
        map[key] = positions.toList()
    }

    override fun <T : Element.Position> get(key: Element<T>): List<Set<T>> {
        @Suppress("UNCHECKED_CAST")
        return map.getValue(key) as List<Set<T>>
    }

    override fun toString() = map.toString()
}
