package me.khol.carcassonne

import me.khol.carcassonne.fixtures.Players
import kotlin.test.Test
import kotlin.test.assertEquals

class ScoreboardTest {

    @Test
    fun `adding scores`() {
        val red = Players.red
        val green = Players.green
        val scoreboard = Scoreboard(players = listOf(red, green))

        with(scoreboard) {
            assertEquals(0, getScore(red))
            assertEquals(0, getScore(green))
        }

        with(scoreboard.addScore(red, 4)) {
            assertEquals(4, getScore(red))
            assertEquals(0, getScore(green))
        }
    }
}
