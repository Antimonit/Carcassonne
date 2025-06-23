package me.khol.carcassonne

import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class RotatedTileTest {

    private val tile = Tiles.Basic.D

    @Test
    fun `check rotation by 0 degrees is idempotent`() {
        val original = RotatedTile(tile, Rotation.ROTATE_0).asTile()
        assertEquals(tile, original)
    }

    @Test
    fun `check rotation by 90 + 270 degrees is idempotent`() {
        val by90 = RotatedTile(tile, Rotation.ROTATE_90).asTile()
        val by360 = RotatedTile(by90, Rotation.ROTATE_270).asTile()
        assertEquals(tile, by360)
    }

    @Test
    fun `check rotation by 180 + 180 degrees is idempotent`() {
        val by180 = RotatedTile(tile, Rotation.ROTATE_180).asTile()
        val by360 = RotatedTile(by180, Rotation.ROTATE_180).asTile()
        assertEquals(tile, by360)
    }

    @Test
    fun `check rotation by 90 degrees`() {
        val original = RotatedTile(tile, Rotation.ROTATE_90).asTile()
        assertEquals(
            expected = Tile(
                name = "D",
                edges = Edges(top = Road, right = City, bottom = Road, left = Field),
                elements = elements {
                    val city = city { right }
                    field(city) { topRight + bottomRight }
                    field { topLeft + bottomLeft + left }
                    road { top + bottom }
                },
            ),
            actual = original,
        )
    }
}
