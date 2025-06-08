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
            checkMatches(tile.edges.top, ElementPosition.Edge.Top, ElementGroup.field { top })
            checkMatches(tile.edges.right, ElementPosition.Edge.Right, ElementGroup.field { right })
            checkMatches(tile.edges.bottom, ElementPosition.Edge.Bottom, ElementGroup.field { bottom })
            checkMatches(tile.edges.left, ElementPosition.Edge.Left, ElementGroup.field { left })

            checkDuplicateEdge(ElementKey.Field)
            checkDuplicateEdge(ElementKey.Road)
            checkDuplicateEdge(ElementKey.City)
            checkDuplicateEdge(ElementKey.River)
        }
    }

    private fun Assertion.Builder<Tile>.checkMatches(
        tileEdge: Tile.Edge,
        edge: ElementPosition.Edge,
        splitEdges: ElementGroup.Field,
    ) = with("since $edge edge is $tileEdge", { elements }) {
        when (tileEdge) {
            Tile.Edge.Field -> {
                getElements(ElementKey.Field).anyContainsAll(splitEdges.positions)
                getElements(ElementKey.Field).anyContainsAll(splitEdges.positions)
                getElements(ElementKey.Road).noneContainsAny(edge)
                getElements(ElementKey.City).noneContainsAny(edge)
                getElements(ElementKey.River).noneContainsAny(edge)
            }
            Tile.Edge.Road -> {
                getElements(ElementKey.Field).and { splitEdges.positions.forEach { anyContainsAll(it) } }
                getElements(ElementKey.Road).anyContainsAll(edge)
                getElements(ElementKey.City).noneContainsAny(edge)
                getElements(ElementKey.River).noneContainsAny(edge)
            }
            Tile.Edge.City -> {
                getElements(ElementKey.Field).and { splitEdges.positions.forEach { noneContainsAny(it) } }
                getElements(ElementKey.Road).noneContainsAny(edge)
                getElements(ElementKey.City).anyContainsAll(edge)
                getElements(ElementKey.River).noneContainsAny(edge)
            }
            Tile.Edge.River -> {
                getElements(ElementKey.Field).and { splitEdges.positions.forEach { anyContainsAll(it) } }
                getElements(ElementKey.Road).noneContainsAny(edge)
                getElements(ElementKey.City).noneContainsAny(edge)
                getElements(ElementKey.River).anyContainsAll(edge)
            }
        }
    }

    private fun <P : ElementPosition, G : ElementGroup<P>> Assertion.Builder<Tile>.checkDuplicateEdge(
        key: ElementKey<P, G>,
    ) = get { elements.get(key) }
        .describedAs { "No $key edge declared multiple times" }
        .get { map { it.positions }.flatten().groupingBy { it }.eachCount().entries }
        .describedAs { "$this" }
        .none { get { value }.isGreaterThan(1) }
}
