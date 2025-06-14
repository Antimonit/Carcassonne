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
            tile.checkMatches(tile.edges.top, ElementPosition.Edge.Top, ElementGroup.field { top })
            tile.checkMatches(tile.edges.right, ElementPosition.Edge.Right, ElementGroup.field { right })
            tile.checkMatches(tile.edges.bottom, ElementPosition.Edge.Bottom, ElementGroup.field { bottom })
            tile.checkMatches(tile.edges.left, ElementPosition.Edge.Left, ElementGroup.field { left })

            assertTrue(tile.checkDuplicateEdge(ElementKey.Field))
            assertTrue(tile.checkDuplicateEdge(ElementKey.Road))
            assertTrue(tile.checkDuplicateEdge(ElementKey.City))
            assertTrue(tile.checkDuplicateEdge(ElementKey.River))
        }
    }

    private fun Tile.checkMatches(
        tileEdge: Tile.Edge,
        edge: ElementPosition.Edge,
        splitEdges: ElementGroup.Field,
    ) = with(elements) {
        when (tileEdge) {
            Tile.Edge.Field -> {
                getElements(ElementKey.Field).anyContainsAll(splitEdges.positions)
                getElements(ElementKey.Field).anyContainsAll(splitEdges.positions)
                getElements(ElementKey.Road).noneContainsAny(edge)
                getElements(ElementKey.City).noneContainsAny(edge)
                getElements(ElementKey.River).noneContainsAny(edge)
            }
            Tile.Edge.Road -> {
                getElements(ElementKey.Field).let {
                    splitEdges.positions.forEach { position -> it.anyContainsAll(position) }
                }
                getElements(ElementKey.Road).anyContainsAll(edge)
                getElements(ElementKey.City).noneContainsAny(edge)
                getElements(ElementKey.River).noneContainsAny(edge)
            }
            Tile.Edge.City -> {
                getElements(ElementKey.Field).let {
                    splitEdges.positions.forEach { position -> it.noneContainsAny(position) }
                }
                getElements(ElementKey.Road).noneContainsAny(edge)
                getElements(ElementKey.City).anyContainsAll(edge)
                getElements(ElementKey.River).noneContainsAny(edge)
            }
            Tile.Edge.River -> {
                getElements(ElementKey.Field).let {
                    splitEdges.positions.forEach { position -> it.anyContainsAll(position) }
                }
                getElements(ElementKey.Road).noneContainsAny(edge)
                getElements(ElementKey.City).noneContainsAny(edge)
                getElements(ElementKey.River).anyContainsAll(edge)
            }
        }
    }

    private fun <P : ElementPosition, G : ElementGroup<P>> Tile.checkDuplicateEdge(
        key: ElementKey<P, G>,
    ): Boolean = elements[key]
        .map { it.positions }.flatten().groupingBy { it }.eachCount().entries
        .none { it.value > 1 }
}
