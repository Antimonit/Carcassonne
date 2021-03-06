package me.khol.carcassonne

import strikt.api.Assertion.Builder
import strikt.assertions.contains

fun <T : Iterable<Iterable<E>>, E> Builder<T>.anyContainsAll(vararg elements: E): Builder<T> =
    anyContainsAll(elements.toList())

infix fun <T : Iterable<Iterable<E>>, E> Builder<T>.anyContainsAll(elements: Collection<E>): Builder<T> =
    compose("any contains the elements %s", elements) {
        subject.forEach { sub ->
            get("%s") { sub }.contains(elements)
        }
    } then {
        if (anyPassed) pass() else fail()
    }


fun <T : Iterable<Iterable<E>>, E> Builder<T>.noneContainsAny(vararg elements: E): Builder<T> =
    noneContainsAny(elements.toList())

infix fun <T : Iterable<Iterable<E>>, E> Builder<T>.noneContainsAny(elements: Collection<E>): Builder<T> =
    compose("none contains the elements %s", elements) {
        subject.forEach { sub ->
            get("%s") { sub }.contains(elements)
        }
    } then {
        if (allFailed) pass() else fail()
    }


fun <P : Position, G : ElementGroup<P>> Builder<Elements>.getElements(element: Element<P, G>): Builder<List<Set<P>>> =
    get { get(element).map { it.value } }.describedAs { "${element.javaClass.simpleName} elements $this" }

