package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.basic.A
import me.khol.carcassonne.tiles.basic.D
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class NeighborElementsTest {

    @Test
    fun `road neighbors`() {
        val board = Board
            .empty
            .placeTile(Coordinates(0, 0), RotatedTile(D.tile, Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 0), RotatedTile(A.tile, Rotation.ROTATE_90), emptyList())

        val placedRoad = PlacedRoad(
            coordinates = Coordinates(1, 0),
            element = A.road.rotate(Rotation.ROTATE_90),
        )

        assertContains(
            iterable = board.getTile(Coordinates(1, 0))!!.elements[Element.Road],
            element = placedRoad.element,
        )

        assertEquals(
            expected = setOf(PlacedRoad(Coordinates(0,0), D.road)),
            actual = placedRoad.neighborElements(tiles = board.tiles, key = Element.Road),
        )
    }

    @Test
    fun `field neighbors`() {
        val board = Board
            .empty
            .placeTile(Coordinates(0, 0), RotatedTile(D.tile, Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 0), RotatedTile(A.tile, Rotation.ROTATE_90), emptyList())

        val placedField = PlacedField(
            coordinates = Coordinates(1, 0),
            element = A.field.rotate(Rotation.ROTATE_90),
        )

        assertContains(
            iterable = board.getTile(Coordinates(1, 0))!!.elements[Element.Field],
            element = placedField.element,
        )

        assertEquals(
            expected = setOf(
                null, // the field has open edges
                PlacedField(Coordinates(0,0), D.fieldTop),
                PlacedField(Coordinates(0,0), D.fieldBottom),
            ),
            actual = placedField.neighborElements(tiles = board.tiles, key = Element.Field),
        )
    }
}
