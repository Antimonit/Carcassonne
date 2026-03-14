package me.khol.carcassonne

import me.khol.carcassonne.fixtures.Players
import kotlin.test.Test
import kotlin.test.assertEquals

class ScoreboardTest {

    @Test
    fun `adding scores`() {
        val red = Players.red
        val green = Players.green

        with(Scoreboard(players = listOf(red, green))) {
            assertEquals(0, getScore(red))
            assertEquals(0, getScore(green))

            with(addScore(red, 4)) {
                assertEquals(4, getScore(red))
                assertEquals(0, getScore(green))

                with(addScores(listOf(red, green), 5)) {
                    assertEquals(9, getScore(red))
                    assertEquals(5, getScore(green))
                }
            }
        }
    }
}
