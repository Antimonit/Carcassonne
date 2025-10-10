package me.khol.carcassonne

interface Elements {

    fun all(): List<Element<*>>

    operator fun <E : Element<ElementPosition>> get(key: ElementKey<E>): List<E>

    fun rotate(rotation: Rotation): Elements
}

interface MutableElements : Elements {

    fun <E : Element<ElementPosition>> add(key: ElementKey<E>, element: E)
}

fun elements(builder: MutableElements.() -> Unit): Elements = ElementsBuilder(
    mutableMapOf()
).apply(builder)

fun MutableElements.add(field: Element.Field) = add(key = Element.Field, element = field)
fun MutableElements.add(road: Element.Road) = add(key = Element.Road, element = road)
fun MutableElements.add(city: Element.City) = add(key = Element.City, element = city)
fun MutableElements.add(monastery: Element.Monastery) = add(key = Element.Monastery, element = monastery)
fun MutableElements.add(garden: Element.Garden) = add(key = Element.Garden, element = garden)
fun MutableElements.add(riverStart: Element.RiverStart) = add(key = Element.RiverStart, element = riverStart)
fun MutableElements.add(river: Element.River) = add(key = Element.River, element = river)
fun MutableElements.add(riverEnd: Element.RiverEnd) = add(key = Element.RiverEnd, element = riverEnd)
fun MutableElements.add(cropCircle: Element.CropCircle) = add(key = Element.CropCircle, element = cropCircle)

fun MutableElements.field(vararg connectedCities: Element.City, block: SplitEdgeBuilder): Element.Field =
    Element.Field(connectedCities = connectedCities, block = block).also(::add)

fun MutableElements.road(vararg boons: Boon.Road, block: EdgeBuilder): Element.Road =
    Element.Road(boons = boons, block = block).also(::add)

fun MutableElements.city(vararg boons: Boon.City, block: EdgeBuilder): Element.City =
    Element.City(boons = boons, block = block).also(::add)

fun MutableElements.monastery(): Element.Monastery =
    Element.Monastery.also(::add)

fun MutableElements.garden(): Element.Garden =
    Element.Garden.also(::add)

fun MutableElements.riverStart(): Element.RiverStart =
    Element.RiverStart.also(::add)

fun MutableElements.river(block: EdgeBuilder): Element.River =
    Element.River(block = block).also(::add)

fun MutableElements.riverEnd(): Element.RiverEnd =
    Element.RiverEnd.also(::add)

fun MutableElements.cropCircle(): Element.CropCircle =
    Element.CropCircle.also(::add)

private data class ElementsBuilder(
    private val map: MutableMap<ElementKey<*>, List<Element<*>>>,
) : MutableElements {

    override fun all(): List<Element<*>> {
        return map.values.flatten()
    }

    override fun <E : Element<ElementPosition>> add(key: ElementKey<E>, element: E) {
        map[key] = get(key) + element
    }

    override fun <E : Element<ElementPosition>> get(key: ElementKey<E>): List<E> {
        @Suppress("UNCHECKED_CAST")
        return map.getOrElse(key) { emptyList<E>() } as List<E>
    }

    override fun rotate(rotation: Rotation): Elements {
        return map
            .mapValues { it.value.map { it.rotate(rotation) } }
            .toMutableMap()
            .let(::ElementsBuilder)
    }

    override fun toString() = map.toString()
}
