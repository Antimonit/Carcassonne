package me.khol.carcassonne

import me.khol.carcassonne.tiles.basic.D
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.test.Test

class ElementsRotationTest {

    @Test
    fun `rotation by 0 degrees is idempotent`() {
        val original = D.elements
        val rotated = original.rotate(Rotation.ROTATE_0)
        expectThat(rotated).isEqualTo(original)
    }

    @Test
    fun `rotation by 90 + 270 degrees is idempotent`() {
        val original = D.elements
        val rotated = original.rotate(Rotation.ROTATE_90).rotate(Rotation.ROTATE_270)
        expectThat(rotated).isEqualTo(original)
    }

    @Test
    fun `rotation by 180 + 180 degrees is idempotent`() {
        val original = D.elements
        val rotated = original.rotate(Rotation.ROTATE_180).rotate(Rotation.ROTATE_180)
        expectThat(rotated).isEqualTo(original)
    }

    @Test
    fun `rotation by 90 + 90 + 90 + 90 degrees is idempotent`() {
        val original = D.elements
        val rotated = original
            .rotate(Rotation.ROTATE_90)
            .rotate(Rotation.ROTATE_90)
            .rotate(Rotation.ROTATE_90)
            .rotate(Rotation.ROTATE_90)
        expectThat(rotated).isEqualTo(original)
    }
}
