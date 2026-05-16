package me.khol.carcassonne

import me.khol.carcassonne.figure.LargeMeeple
import me.khol.carcassonne.figure.Mayor
import me.khol.carcassonne.figure.Meeple
import me.khol.carcassonne.fixtures.Players
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class MaxPresenceTest {

    private val board = Board.starting(startingTile = Tiles.Basic.D.tile)

    private val cityFeature = board.cityFeatures.first()
    private val cityFeatureWithTwoCoatOfArms = board
        .placeTile(Tiles.Basic.C.tile.rotated(Rotation.ROTATE_0).placed(0, -1), emptyList())
        .placeTile(Tiles.Basic.C.tile.rotated(Rotation.ROTATE_0).placed(0, -2), emptyList())
        .cityFeatures.first()

    @Test
    fun `single player with one meeple wins alone`() {
        assertEquals(
            expected = setOf(Players.red),
            actual = listOf(
                PlayerFigure(Meeple, Players.red),
            ).maxPresence(cityFeature),
        )
    }

    @Test
    fun `two players with equal meeple counts tie`() {
        assertEquals(
            expected = setOf(Players.red, Players.green),
            actual = listOf(
                PlayerFigure(Meeple, Players.red),
                PlayerFigure(Meeple, Players.green),
            ).maxPresence(cityFeature),
        )
    }

    @Test
    fun `player with more meeples wins`() {
        assertEquals(
            expected = setOf(Players.red),
            actual = listOf(
                PlayerFigure(Meeple, Players.red),
                PlayerFigure(Meeple, Players.red),
                PlayerFigure(Meeple, Players.green),
            ).maxPresence(cityFeature),
        )
    }

    @Test
    fun `large meeple beats a single meeple`() {
        assertEquals(
            expected = setOf(Players.red),
            actual = listOf(
                PlayerFigure(LargeMeeple, Players.red),
                PlayerFigure(Meeple, Players.green),
            ).maxPresence(cityFeature),
        )
    }

    @Test
    fun `large meeple ties with two meeples`() {
        assertEquals(
            expected = setOf(Players.red, Players.green),
            actual = listOf(
                PlayerFigure(LargeMeeple, Players.red),
                PlayerFigure(Meeple, Players.green),
                PlayerFigure(Meeple, Players.green)
            ).maxPresence(cityFeature),
        )
    }

    @Test
    fun `mayor has zero presence in city without coat of arms`() {
        assertEquals(
            setOf(element = Players.green),
            listOf(
                PlayerFigure(Mayor, Players.red),
                PlayerFigure(Meeple, Players.green)
            ).maxPresence (cityFeature),
        )
    }

    @Test
    fun `mayor presence equals coat of arms count in city`() {
        assertEquals(
            expected = setOf(Players.red, Players.green),
            actual = listOf(
                PlayerFigure(Mayor, Players.red),
                PlayerFigure(Meeple, Players.green),
                PlayerFigure(Meeple, Players.green)
            ).maxPresence(cityFeatureWithTwoCoatOfArms),
        )
    }
}