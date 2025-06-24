package me.khol.carcassonne

interface Elements {

    operator fun <P : ElementPosition, G : ElementGroup<P>> get(key: ElementKey<P, G>): List<G>

    fun rotate(rotation: Rotation): Elements
}

interface MutableElements : Elements {

    fun <P : ElementPosition, G : ElementGroup<P>> add(key: ElementKey<P, G>, group: G)
}

fun elements(builder: MutableElements.() -> Unit): Elements = ElementsBuilder(
    mutableMapOf()
).apply(builder)

fun MutableElements.add(field: ElementGroup.Field) = add(key = ElementKey.Field, group = field)
fun MutableElements.add(road: ElementGroup.Road) = add(key = ElementKey.Road, group = road)
fun MutableElements.add(city: ElementGroup.City) = add(key = ElementKey.City, group = city)
fun MutableElements.add(monastery: ElementGroup.Monastery) = add(key = ElementKey.Monastery, group = monastery)
fun MutableElements.add(garden: ElementGroup.Garden) = add(key = ElementKey.Garden, group = garden)
fun MutableElements.add(riverStart: ElementGroup.RiverStart) = add(key = ElementKey.RiverStart, group = riverStart)
fun MutableElements.add(river: ElementGroup.River) = add(key = ElementKey.River, group = river)
fun MutableElements.add(riverEnd: ElementGroup.RiverEnd) = add(key = ElementKey.RiverEnd, group = riverEnd)
fun MutableElements.add(cropCircle: ElementGroup.CropCircle) = add(key = ElementKey.CropCircle, group = cropCircle)

fun MutableElements.field(vararg connectedCities: ElementGroup.City, block: SplitEdgeBuilder): ElementGroup.Field =
    ElementGroup.Field(connectedCities = connectedCities, block = block).also(::add)

fun MutableElements.road(vararg boons: Boon.Road, block: EdgeBuilder): ElementGroup.Road =
    ElementGroup.Road(boons = boons, block = block).also(::add)

fun MutableElements.city(vararg boons: Boon.City, block: EdgeBuilder): ElementGroup.City =
    ElementGroup.City(boons = boons, block = block).also(::add)

fun MutableElements.monastery(): ElementGroup.Monastery =
    ElementGroup.Monastery.also(::add)

fun MutableElements.garden(): ElementGroup.Garden =
    ElementGroup.Garden.also(::add)

fun MutableElements.riverStart(): ElementGroup.RiverStart =
    ElementGroup.RiverStart.also(::add)

fun MutableElements.river(block: EdgeBuilder): ElementGroup.River =
    ElementGroup.River(block = block).also(::add)

fun MutableElements.riverEnd(): ElementGroup.RiverEnd =
    ElementGroup.RiverEnd.also(::add)

fun MutableElements.cropCircle(): ElementGroup.CropCircle =
    ElementGroup.CropCircle.also(::add)

private data class ElementsBuilder(
    private val map: MutableMap<ElementKey<*, *>, List<ElementGroup<*>>>
) : MutableElements {

    override fun <P : ElementPosition, G : ElementGroup<P>> add(key: ElementKey<P, G>, group: G) {
        map[key] = get(key) + group
    }

    override fun <P : ElementPosition, G : ElementGroup<P>> get(key: ElementKey<P, G>): List<G> {
        @Suppress("UNCHECKED_CAST")
        return map.getOrElse(key) { emptyList<G>() } as List<G>
    }

    override fun rotate(rotation: Rotation): Elements {
        return map
            .mapValues { it.value.map { it.rotate(rotation) } }
            .toMutableMap()
            .let(::ElementsBuilder)
    }

    override fun toString() = map.toString()
}
