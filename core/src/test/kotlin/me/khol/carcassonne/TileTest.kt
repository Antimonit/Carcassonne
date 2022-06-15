package me.khol.carcassonne

import me.khol.carcassonne.tiles.basic.*
import me.khol.carcassonne.tiles.crops.*
import me.khol.carcassonne.tiles.river.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import strikt.api.Assertion
import strikt.api.expectThat
import strikt.assertions.isGreaterThan
import strikt.assertions.none

internal class TileTest {

    companion object {
        @JvmStatic
        fun `tile edges match tile elements`() =
            setOf(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X) +
                setOf(BB6F1, BB6F2, BB6F3, BB6F4, BB6F5, BB6F6, BB6F7, BB6F8, BB6F9, BB6F10, BB6F11, BB6F12) +
                setOf(Korn1, Korn2, Korn3, Korn4, Korn5, Korn6)
    }

    @ParameterizedTest
    @MethodSource
    fun `tile edges match tile elements`(tile: Tile) {
        expectThat(tile) {
            checkMatches(tile.edges.top, Position.Edge.Top, Positions.field { top })
            checkMatches(tile.edges.right, Position.Edge.Right, Positions.field { right })
            checkMatches(tile.edges.bottom, Position.Edge.Bottom, Positions.field { bottom })
            checkMatches(tile.edges.left, Position.Edge.Left, Positions.field { left })

            checkDuplicateEdge(Element.Field)
            checkDuplicateEdge(Element.Road)
            checkDuplicateEdge(Element.City)
            checkDuplicateEdge(Element.River)
        }
    }

    private fun Assertion.Builder<Tile>.checkMatches(
        tileEdge: Tile.Edge,
        edge: Position.Edge,
        splitEdges: Positions.Field,
    ) = with("since $edge edge is $tileEdge", { elements }) {
        when (tileEdge) {
            Tile.Edge.Field -> {
                getElements(Element.Field).anyContainsAll(splitEdges.value)
                getElements(Element.Field).anyContainsAll(splitEdges.value)
                getElements(Element.Road).noneContainsAny(edge)
                getElements(Element.City).noneContainsAny(edge)
                getElements(Element.River).noneContainsAny(edge)
            }
            Tile.Edge.Road -> {
                getElements(Element.Field).and { splitEdges.value.forEach { anyContainsAll(it) } }
                getElements(Element.Road).anyContainsAll(edge)
                getElements(Element.City).noneContainsAny(edge)
                getElements(Element.River).noneContainsAny(edge)
            }
            Tile.Edge.City -> {
                getElements(Element.Field).and { splitEdges.value.forEach { noneContainsAny(it) } }
                getElements(Element.Road).noneContainsAny(edge)
                getElements(Element.City).anyContainsAll(edge)
                getElements(Element.River).noneContainsAny(edge)
            }
            Tile.Edge.River -> {
                getElements(Element.Field).and { splitEdges.value.forEach { anyContainsAll(it) } }
                getElements(Element.Road).noneContainsAny(edge)
                getElements(Element.City).noneContainsAny(edge)
                getElements(Element.River).anyContainsAll(edge)
            }
        }
    }

    private fun <P : Position, PS : Positions<P>> Assertion.Builder<Tile>.checkDuplicateEdge(
        field: Element<P, PS>,
    ) = get { elements[field] }
        .describedAs { "No $field edge declared multiple times" }
        .get { map { it.value }.flatten().groupingBy { it }.eachCount().entries }
        .describedAs { "$this" }
        .none { get { value }.isGreaterThan(1) }
}
