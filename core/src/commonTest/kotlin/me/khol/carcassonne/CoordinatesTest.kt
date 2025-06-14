package me.khol.carcassonne

import kotlin.test.Test
import kotlin.test.assertEquals

class CoordinatesTest {

    @Test
    fun surroundingCoordinates() {
        assertEquals(
            expected = setOf(
                Coordinates(x = -1, y = 4),
                Coordinates(x = -2, y = 4),
                Coordinates(x = -3, y = 4),
                Coordinates(x = -1, y = 5),
                Coordinates(x = -2, y = 5),
                Coordinates(x = -3, y = 5),
                Coordinates(x = -1, y = 6),
                Coordinates(x = -2, y = 6),
                Coordinates(x = -3, y = 6),
            ),
            actual = Coordinates(x = -2, y = 5).surroundingCoordinates(),
        )
    }
}
