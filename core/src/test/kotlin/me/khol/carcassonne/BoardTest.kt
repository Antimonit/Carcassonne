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
        val board = Board.starting(startingTile = D)
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
                    PlacedTile(RotatedTile(newTile, Rotation.ROTATE_0), Coordinates(x = +0, y = -1)),
                    PlacedTile(RotatedTile(newTile, Rotation.ROTATE_90), Coordinates(x = +0, y = -1)),
                    PlacedTile(RotatedTile(newTile, Rotation.ROTATE_270), Coordinates(x = +0, y = -1)),
                    PlacedTile(RotatedTile(newTile, Rotation.ROTATE_270), Coordinates(x = -1, y = +0)),
                    PlacedTile(RotatedTile(newTile, Rotation.ROTATE_90), Coordinates(x = +1, y = +0)),
                )
            }
    }

    @Test
    fun `placing a tile updates open spaces`() {
        val board = Board.starting(startingTile = D)
        val newTile = F
        val newBoard = board.placeTile(Coordinates(x = 0, y = -1), RotatedTile(newTile, Rotation.ROTATE_0))
        expectThat(newBoard)
            .get { possibleSpacesForTile(newTile) }
            .get { keys }
            .containsExactlyInAnyOrder(
                Coordinates(x = 0, y = 1),
                Coordinates(x = -1, y = -1),
                Coordinates(x = 1, y = -1),
                Coordinates(x = 0, y = -2),
            )
    }
}
