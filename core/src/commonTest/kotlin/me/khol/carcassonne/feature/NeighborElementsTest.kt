package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class NeighborElementsTest {

    @Test
    fun `road neighbors`() {
        val board = Board
            .empty
            .placeTile(Coordinates(0, 0), Tiles.Basic.D.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 0), Tiles.Basic.A.tile.rotated(Rotation.ROTATE_90), emptyList())

        val placedRoad = PlacedRoad(
            coordinates = Coordinates(1, 0),
            rotatedElement = Tiles.Basic.A.road.rotated(Rotation.ROTATE_90),
        )

        assertContains(
            iterable = board.getTile(Coordinates(1, 0))!!.rotatedElements.all(),
            element = placedRoad.rotatedElement,
        )

        assertEquals(
            expected = setOf(PlacedRoad(Coordinates(0,0), Tiles.Basic.D.road.rotated(rotation = Rotation.ROTATE_0))),
            actual = placedRoad.neighborElements(board = board, key = Element.Road),
        )
    }

    @Test
    fun `field neighbors`() {
        val board = Board
            .empty
            .placeTile(Coordinates(0, 0), Tiles.Basic.D.tile.rotated(Rotation.ROTATE_0), emptyList())
            .placeTile(Coordinates(1, 0), Tiles.Basic.A.tile.rotated(Rotation.ROTATE_90), emptyList())

        val placedField = PlacedField(
            coordinates = Coordinates(1, 0),
            rotatedElement = Tiles.Basic.A.field.rotated(Rotation.ROTATE_90),
        )

        assertContains(
            iterable = board.getTile(Coordinates(1, 0))!!.rotatedElements.all(),
            element = placedField.rotatedElement,
        )

        assertEquals(
            expected = setOf(
                null, // the field has open edges
                PlacedField(Coordinates(0,0), Tiles.Basic.D.fieldTop.rotated(rotation = Rotation.ROTATE_0)),
                PlacedField(Coordinates(0,0), Tiles.Basic.D.fieldBottom.rotated(rotation = Rotation.ROTATE_0)),
            ),
            actual = placedField.neighborElements(board = board, key = Element.Field),
        )
    }
}
