package me.khol.carcassonne

import me.khol.carcassonne.tiles.basic.D
import kotlin.test.Test
import kotlin.test.assertEquals

class TileEdgesRotationTest {

    @Test
    fun `edge rotation by 0 degrees is idempotent`() {
        val original = D.edges
        val rotated = original.rotate(Rotation.ROTATE_0)
        assertEquals(original, rotated)
    }

    @Test
    fun `edge rotation by 90 + 270 degrees is idempotent`() {
        val original = D.edges
        val rotated = original.rotate(Rotation.ROTATE_90).rotate(Rotation.ROTATE_270)
        assertEquals(original, rotated)
    }

    @Test
    fun `edge rotation by 180 + 180 degrees is idempotent`() {
        val original = D.edges
        val rotated = original.rotate(Rotation.ROTATE_180).rotate(Rotation.ROTATE_180)
        assertEquals(original, rotated)
    }

    @Test
    fun `edge rotation by 90 + 90 + 90 + 90 degrees is idempotent`() {
        val original = D.edges
        val rotated = original
            .rotate(Rotation.ROTATE_90)
            .rotate(Rotation.ROTATE_90)
            .rotate(Rotation.ROTATE_90)
            .rotate(Rotation.ROTATE_90)
        assertEquals(original, rotated)
    }
}