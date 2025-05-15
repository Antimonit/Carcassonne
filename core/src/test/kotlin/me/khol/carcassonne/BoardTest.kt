package me.khol.carcassonne

import me.khol.carcassonne.tiles.basic.A
import me.khol.carcassonne.tiles.basic.D
import me.khol.carcassonne.tiles.basic.F
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder

internal class BoardTest {

    @Test
    fun `possible spaces for a tile`() {
        val board = Board(startingTile = D)
        val newTile = A
        expectThat(board)
            .get { possibleSpacesForTile(newTile) }
            .and {
                get { keys }.containsExactlyInAnyOrder(
                    Coordinates(x = +0, y = -1),
                    Coordinates(x = -1, y = +0),
                    Coordinates(x = +1, y = +0),
                )
                get { values.flatten() }.containsExactlyInAnyOrder(
                    PlacedTile(Coordinates(x = +0, y = -1), RotatedTile(newTile, Rotation.ROTATE_0)),
                    PlacedTile(Coordinates(x = +0, y = -1), RotatedTile(newTile, Rotation.ROTATE_90)),
                    PlacedTile(Coordinates(x = +0, y = -1), RotatedTile(newTile, Rotation.ROTATE_270)),
                    PlacedTile(Coordinates(x = -1, y = +0), RotatedTile(newTile, Rotation.ROTATE_270)),
                    PlacedTile(Coordinates(x = +1, y = +0), RotatedTile(newTile, Rotation.ROTATE_90)),
                )
            }
    }
}
