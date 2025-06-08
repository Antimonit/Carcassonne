package me.khol.carcassonne

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder

class CoordinatesTest {

    @Test
    fun surroundingCoordinates() {
        expectThat(Coordinates(x = -2, y = 5))
            .get { surroundingCoordinates() }
            .containsExactlyInAnyOrder(
                Coordinates(x = -1, y = 4),
                Coordinates(x = -2, y = 4),
                Coordinates(x = -3, y = 4),
                Coordinates(x = -1, y = 5),
                Coordinates(x = -2, y = 5),
                Coordinates(x = -3, y = 5),
                Coordinates(x = -1, y = 6),
                Coordinates(x = -2, y = 6),
                Coordinates(x = -3, y = 6),
            )
    }
}
