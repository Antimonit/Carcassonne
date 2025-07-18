package me.khol.carcassonne

import me.khol.carcassonne.tiles.basicTileset
import me.khol.carcassonne.tiles.cropsTileset
import me.khol.carcassonne.tiles.innsTileset
import me.khol.carcassonne.tiles.riverTileset
import kotlin.test.Test
import kotlin.test.assertTrue

internal class TileTest {

    @Test
    fun `tile edges match tile elements`() {
        val tiles = listOf(basicTileset, riverTileset, innsTileset, cropsTileset)
            .flatMap { it.tileCounts }
            .map { it.tile }

        tiles.forEach { tile ->
            tile.checkMatches(tile.edges.top, ElementPosition.Edge.Top, Element.Field { top })
            tile.checkMatches(tile.edges.right, ElementPosition.Edge.Right, Element.Field { right })
            tile.checkMatches(tile.edges.bottom, ElementPosition.Edge.Bottom, Element.Field { bottom })
            tile.checkMatches(tile.edges.left, ElementPosition.Edge.Left, Element.Field { left })

            assertTrue(tile.checkDuplicateEdge(Element.Field))
            assertTrue(tile.checkDuplicateEdge(Element.Road))
            assertTrue(tile.checkDuplicateEdge(Element.City))
            assertTrue(tile.checkDuplicateEdge(Element.River))
        }
    }

    private fun Tile.checkMatches(
        tileEdge: Tile.Edge,
        edge: ElementPosition.Edge,
        splitEdges: Element.Field,
    ) = with(elements) {
        when (tileEdge) {
            Tile.Edge.Field -> {
                getElements(Element.Field).anyContainsAll(splitEdges.positions)
                getElements(Element.Field).anyContainsAll(splitEdges.positions)
                getElements(Element.Road).noneContainsAny(edge)
                getElements(Element.City).noneContainsAny(edge)
                getElements(Element.River).noneContainsAny(edge)
            }
            Tile.Edge.Road -> {
                getElements(Element.Field).let {
                    splitEdges.positions.forEach { position -> it.anyContainsAll(position) }
                }
                getElements(Element.Road).anyContainsAll(edge)
                getElements(Element.City).noneContainsAny(edge)
                getElements(Element.River).noneContainsAny(edge)
            }
            Tile.Edge.City -> {
                getElements(Element.Field).let {
                    splitEdges.positions.forEach { position -> it.noneContainsAny(position) }
                }
                getElements(Element.Road).noneContainsAny(edge)
                getElements(Element.City).anyContainsAll(edge)
                getElements(Element.River).noneContainsAny(edge)
            }
            Tile.Edge.River -> {
                getElements(Element.Field).let {
                    splitEdges.positions.forEach { position -> it.anyContainsAll(position) }
                }
                getElements(Element.Road).noneContainsAny(edge)
                getElements(Element.City).noneContainsAny(edge)
                getElements(Element.River).anyContainsAll(edge)
            }
        }
    }

    private fun <E : Element<ElementPosition>> Tile.checkDuplicateEdge(
        key: ElementKey<E>,
    ): Boolean = elements[key]
        .map { it.positions }.flatten().groupingBy { it }.eachCount().entries
        .none { it.value > 1 }
}
