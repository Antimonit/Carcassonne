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


fun MutableElements.field(vararg connectedCities: ElementGroup.City, block: ElementGroup.Field.Builder.Companion.() -> ElementGroup.Field.Builder): ElementGroup.Field {
    val field = ElementGroup.field(connectedCities = connectedCities, block = block)
    add(key = Element.Field, field)
    return field
}

fun MutableElements.road(vararg boons: Boon.Road, block: ElementGroup.Road.Builder.Companion.() -> ElementGroup.Road.Builder): ElementGroup.Road {
    val road = ElementGroup.road(boons = boons, block = block)
    add(key = Element.Road, road)
    return road
}

fun MutableElements.city(vararg boons: Boon.City, block: ElementGroup.City.Builder.Companion.() -> ElementGroup.City.Builder): ElementGroup.City {
    val city = ElementGroup.city(boons = boons, block = block)
    add(key = Element.City, city)
    return city
}

fun MutableElements.monastery(): ElementGroup.Center {
    val monastery = ElementGroup.Center
    add(key = Element.Monastery, monastery)
    return monastery
}

fun MutableElements.garden(): ElementGroup.Center {
    val garden = ElementGroup.Center
    add(key = Element.Garden, garden)
    return garden
}

fun MutableElements.riverStart(): ElementGroup.Center {
    val riverStart = ElementGroup.Center
    add(key = Element.RiverStart, riverStart)
    return riverStart
}

fun MutableElements.river(block: ElementGroup.Edge.Builder.Companion.() -> ElementGroup.Edge.Builder): ElementGroup.Edge {
    val river = ElementGroup.edges(block = block)
    add(key = Element.River, river)
    return river
}

fun MutableElements.riverEnd(): ElementGroup.Center {
    val riverEnd = ElementGroup.Center
    add(key = Element.RiverEnd, riverEnd)
    return riverEnd
}

fun MutableElements.cropCircle(): ElementGroup.Center {
    val cropCircle = ElementGroup.Center
    add(key = Element.CropCircle, cropCircle)
    return cropCircle
}

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
