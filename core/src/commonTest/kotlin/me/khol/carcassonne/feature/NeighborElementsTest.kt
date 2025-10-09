package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.placed
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
            .placeTile(Tiles.Basic.D.tile.rotated(Rotation.ROTATE_0).placed(0, 0), emptyList())
            .placeTile(Tiles.Basic.A.tile.rotated(Rotation.ROTATE_90).placed(1, 0), emptyList())

        val placedRoad = Tiles.Basic.A.road.rotated(Rotation.ROTATE_90).placed(1, 0)

        assertContains(
            iterable = board.getTile(Coordinates(1, 0))!!.rotatedElements.all(),
            element = placedRoad.rotatedElement,
        )

        assertEquals(
            expected = setOf(Tiles.Basic.D.road.rotated(rotation = Rotation.ROTATE_0).placed(0,0)),
            actual = placedRoad.neighborElements(tiles = board.tiles, key = Element.Road),
        )
    }

    @Test
    fun `field neighbors`() {
        val board = Board
            .empty
            .placeTile(Tiles.Basic.D.tile.rotated(Rotation.ROTATE_0).placed(0, 0), emptyList())
            .placeTile(Tiles.Basic.A.tile.rotated(Rotation.ROTATE_90).placed(1, 0), emptyList())

        val placedField = Tiles.Basic.A.field.rotated(Rotation.ROTATE_90).placed(1, 0)

        assertContains(
            iterable = board.getTile(Coordinates(1, 0))!!.rotatedElements.all(),
            element = placedField.rotatedElement,
        )

        assertEquals(
            expected = setOf(
                null, // the field has open edges
                Tiles.Basic.D.fieldTop.rotated(rotation = Rotation.ROTATE_0).placed(0,0),
                Tiles.Basic.D.fieldBottom.rotated(rotation = Rotation.ROTATE_0).placed(0,0),
            ),
            actual = placedField.neighborElements(tiles = board.tiles, key = Element.Field),
        )
    }
}
