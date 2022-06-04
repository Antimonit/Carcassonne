package me.khol.carcassonne

interface Elements {

    operator fun <P : Position, Set : Positions<P>> get(key: Element<P>): List<Set>
}

interface MutableElements : Elements {

    fun <P : Position, Set : Positions<P>> add(key: Element<P>, vararg positions: Set)
}

fun elements(builder: MutableElements.() -> Unit): Elements = ElementsBuilder().apply(builder)

private class ElementsBuilder : MutableElements {

    private val map = mutableMapOf<Element<*>, List<*>>().withDefault { emptyList<Any>() }

    override fun <P : Position, Set : Positions<P>> add(key: Element<P>, vararg positions: Set) {
        map[key] = positions.toList()
    }

    override fun <P : Position, Set : Positions<P>> get(key: Element<P>): List<Set> {
        @Suppress("UNCHECKED_CAST")
        return map.getValue(key) as List<Set>
    }

    override fun toString() = map.toString()
}
