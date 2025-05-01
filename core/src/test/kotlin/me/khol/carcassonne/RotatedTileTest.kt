package me.khol.carcassonne

import me.khol.carcassonne.Tile.Edge.City
import me.khol.carcassonne.Tile.Edge.Field
import me.khol.carcassonne.Tile.Edge.Road
import me.khol.carcassonne.Tile.Edges
import me.khol.carcassonne.tiles.basic.D
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class RotatedTileTest {

    @Test
    fun `check rotation by 0 degrees is idempotent`() {
        val tile = D
        val original = RotatedTile(tile, Rotation.ROTATE_0)
        expectThat(original).get { rotatedTile }.isEqualTo(tile)
    }

    @Test
    fun `check rotation by 90 + 270 degrees is idempotent`() {
        val tile = D
        val by90 = RotatedTile(tile, Rotation.ROTATE_90)
        val by360 = RotatedTile(by90.rotatedTile, Rotation.ROTATE_270)
        expectThat(by360).get { rotatedTile }.isEqualTo(tile)
    }

    @Test
    fun `check rotation by 180 + 180 degrees is idempotent`() {
        val tile = D
        val by180 = RotatedTile(tile, Rotation.ROTATE_180)
        val by360 = RotatedTile(by180.rotatedTile, Rotation.ROTATE_180)
        expectThat(by360).get { rotatedTile }.isEqualTo(tile)
    }

    @Test
    fun `check rotation by 90 degrees`() {
        val tile = D
        val original = RotatedTile(tile, Rotation.ROTATE_90)
        expectThat(original).get { rotatedTile }.isEqualTo(
            Tile(
                name = "D",
                edges = Edges(top = Road, right = City, bottom = Road, left = Field),
                elements = elements {
                    val city = ElementGroup.city { right }
                    add(
                        Element.Field,
                        ElementGroup.field(city) { topRight + bottomRight },
                        ElementGroup.field { topLeft + bottomLeft + left },
                    )
                    add(
                        Element.City,
                        city,
                    )
                    add(
                        Element.Road,
                        ElementGroup.road { top + bottom },
                    )
                },
            )
        )
    }
}
