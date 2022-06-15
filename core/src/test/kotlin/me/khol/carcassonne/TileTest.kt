package me.khol.carcassonne

import me.khol.carcassonne.tiles.basicTileset
import me.khol.carcassonne.tiles.cropsTileset
import me.khol.carcassonne.tiles.innsTileset
import me.khol.carcassonne.tiles.riverTileset
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
            listOf(basicTileset, riverTileset, innsTileset, cropsTileset)
                .flatMap { it.tileCounts }
                .map { it.tile }
    }

    @ParameterizedTest
    @MethodSource
    fun `tile edges match tile elements`(tile: Tile) {
        expectThat(tile) {
            checkMatches(tile.edges.top, Position.Edge.Top, ElementGroup.field { top })
            checkMatches(tile.edges.right, Position.Edge.Right, ElementGroup.field { right })
            checkMatches(tile.edges.bottom, Position.Edge.Bottom, ElementGroup.field { bottom })
            checkMatches(tile.edges.left, Position.Edge.Left, ElementGroup.field { left })

            checkDuplicateEdge(Element.Field)
            checkDuplicateEdge(Element.Road)
            checkDuplicateEdge(Element.City)
            checkDuplicateEdge(Element.River)
        }
    }

    private fun Assertion.Builder<Tile>.checkMatches(
        tileEdge: Tile.Edge,
        edge: Position.Edge,
        splitEdges: ElementGroup.Field,
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

    private fun <P : Position, G : ElementGroup<P>> Assertion.Builder<Tile>.checkDuplicateEdge(
        field: Element<P, G>,
    ) = get { elements[field] }
        .describedAs { "No $field edge declared multiple times" }
        .get { map { it.value }.flatten().groupingBy { it }.eachCount().entries }
        .describedAs { "$this" }
        .none { get { value }.isGreaterThan(1) }
}
