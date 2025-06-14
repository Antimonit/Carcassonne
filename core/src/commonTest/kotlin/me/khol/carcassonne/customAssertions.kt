package me.khol.carcassonne

fun <T : Iterable<Iterable<E>>, E> T.anyContainsAll(vararg elements: E): Boolean =
    anyContainsAll(elements.toList())
fun <T : Iterable<Iterable<E>>, E> T.anyContainsAll(elements: Collection<E>): Boolean =
    any { it.containsAll(elements) }

fun <T : Iterable<Iterable<E>>, E> T.noneContainsAny(vararg elements: E): Boolean =
    noneContainsAny(elements.toList())
fun <T : Iterable<Iterable<E>>, E> T.noneContainsAny(elements: Collection<E>): Boolean =
    none { it.containsAny(elements) }

fun <T : Iterable<E>, E> T.containsAll(elements: Iterable<E>): Boolean =
    elements.all { it in this }

fun <T : Iterable<E>, E> T.containsAny(elements: Iterable<E>): Boolean =
    elements.any { it in this }

fun <P : ElementPosition, G : ElementGroup<P>> Elements.getElements(key: ElementKey<P, G>): List<Set<P>> =
    get(key).map { it.positions }
