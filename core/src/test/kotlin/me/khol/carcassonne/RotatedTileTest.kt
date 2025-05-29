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

    private val tile = D

    @Test
    fun `check rotation by 0 degrees is idempotent`() {
        val original = RotatedTile(tile, Rotation.ROTATE_0).asTile()
        expectThat(original).isEqualTo(tile)
    }

    @Test
    fun `check rotation by 90 + 270 degrees is idempotent`() {
        val by90 = RotatedTile(tile, Rotation.ROTATE_90).asTile()
        val by360 = RotatedTile(by90, Rotation.ROTATE_270).asTile()
        expectThat(by360).isEqualTo(tile)
    }

    @Test
    fun `check rotation by 180 + 180 degrees is idempotent`() {
        val by180 = RotatedTile(tile, Rotation.ROTATE_180).asTile()
        val by360 = RotatedTile(by180, Rotation.ROTATE_180).asTile()
        expectThat(by360).isEqualTo(tile)
    }

    @Test
    fun `check rotation by 90 degrees`() {
        val original = RotatedTile(tile, Rotation.ROTATE_90).asTile()
        expectThat(original).isEqualTo(
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
