package me.khol.carcassonne

import app.cash.turbine.test
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import me.khol.carcassonne.feature.City
import me.khol.carcassonne.feature.Road
import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.fixtures.Players
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basicTileset
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class EngineTest {

    private val dispatcher: TestDispatcher = StandardTestDispatcher()

    val playerRed = Players.red
    val playerGreen = Players.green
    val engine = Engine(
        initialGame = Game.new(
            tilesets = listOf(basicTileset),
            startingTile = Tiles.Basic.D.tile,
            players = listOf(playerRed, playerGreen)
        ),
        dispatcher = dispatcher,
    )

    @Test
    fun `undo placing an unplaced tile has no effect`() {
        assertIs<Phase.PlacingTile>(engine.game.value.phase).also {
            assertNull(it.placedTile)
        }
        engine.undo()
        assertIs<Phase.PlacingTile>(engine.game.value.phase).also {
            assertNull(it.placedTile)
        }
    }

    @Test
    fun `undo placing a placed tile`() {
        val tile = Tiles.Basic.D.tile
        val placedTile = tile.rotated(Rotation.ROTATE_0).placed(1, 0)
        engine.placeTile(
            phase = engine.game.value.placingTile(tile = tile, placedTile = placedTile),
            placedTile = placedTile,
        )
        assertIs<Phase.PlacingTile>(engine.game.value.phase).also {
            assertNotNull(it.placedTile)
        }
        engine.undo()
        assertIs<Phase.PlacingTile>(engine.game.value.phase).also {
            assertNull(it.placedTile)
        }
    }

    @Test
    fun `undo placing a meeple`() {
        val placedTile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_0).placed(1, 0)
        engine.placeFigure(
            phase = engine.game.value.placingFigure(placedTile),
            placedFigure = PlacedFigure(
                placedElement = Tiles.Basic.D.road.rotated(Rotation.ROTATE_0).placed(1, 0),
                figure = PlayerFigures.greenMeeple,
            )
        )
        assertIs<Phase.PlacingFigure>(engine.game.value.phase).also {
            assertNotNull(it.placedFigure)
        }
        engine.undo()
        assertIs<Phase.PlacingFigure>(engine.game.value.phase).also {
            assertNull(it.placedFigure)
        }
        engine.undo()
        assertIs<Phase.PlacingTile>(engine.game.value.phase).also {
            assertNotNull(it.placedTile)
        }
        engine.undo()
        assertIs<Phase.PlacingTile>(engine.game.value.phase).also {
            assertNull(it.placedTile)
        }
    }

    private fun Game.placingTile(tile: Tile, placedTile: PlacedTile? = null) =
        Phase.PlacingTile(
            tile = tile,
            validTilePlacements = board.possibleSpacesForTile(tile),
            placedTile = placedTile,
        )

    private fun Game.placingFigure(placedTile: PlacedTile, placedFigure: PlacedFigure? = null) =
        Phase.PlacingFigure(
            placedTile = placedTile,
            validTilePlacements = board.possibleSpacesForTile(placedTile.rotatedTile.tile),
            validFigurePlacements = board.validFigurePlacements(
                placedTile = placedTile,
                currentPlayer = currentPlayer,
                figureSupply = figureSupply,
            ),
            placedFigure = placedFigure,
        )

    @Test
    fun `placing a tile changes the current player`() = runTest(dispatcher) {
        assertEquals(playerRed, engine.game.value.currentPlayer)
        val tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_0)
        engine.confirmFigurePlacement(
            phase = engine.game.value.placingFigure(tile.placed(1, 0))
        )
        testScheduler.runCurrent()
        assertEquals(playerGreen, engine.game.value.currentPlayer)
        engine.confirmFigurePlacement(
            phase = engine.game.value.placingFigure(tile.placed(2, 0))
        )
        testScheduler.runCurrent()
        assertEquals(playerRed, engine.game.value.currentPlayer)
    }

    @Test
    fun `placing a tile scores all features`() = runTest(dispatcher) {
        val tileL = Tiles.Basic.L.tile.rotated(Rotation.ROTATE_270)
        val tileP = Tiles.Basic.P.tile.rotated(Rotation.ROTATE_180)

        engine.game.test {
            awaitItem()

            engine.confirmFigurePlacement(
                phase = engine.game.value.placingFigure(tileL.placed(-1, 0))
            )
            awaitItem()

            val greenFigure = PlacedFigure(
                placedElement = Tiles.Basic.P.road.rotated(Rotation.ROTATE_180).placed(1, 0),
                figure = PlayerFigures.greenMeeple,
            )
            engine.confirmFigurePlacement(
                phase = engine.game.value.placingFigure(tileP.placed(1, 0), greenFigure)
            )
            awaitItem()

            val redFigure = PlacedFigure(
                placedElement = Tiles.Basic.P.city.rotated(Rotation.ROTATE_180).placed(0, -1),
                figure = PlayerFigures.redMeeple,
            )
            engine.confirmFigurePlacement(
                phase = engine.game.value.placingFigure(tileP.placed(0, -1), redFigure)
            )
            with(awaitItem()) {
                assertIs<Phase.PlacingTile>(phase).also {
                    assertNull(it.placedTile)
                }
                assertIs<History.Event.TilePlacement>(history.events.last())
            }
            expectNoEvents()

            // This tile placement triggers scoring for two players for two separate features.
            engine.confirmFigurePlacement(
                phase = engine.game.value.placingFigure(tileL.placed(1, -1))
            )

            // Zoom on to the green player scoring but do not update scoreboard or history yet.
            with(awaitItem()) {
                assertIs<Phase.Scoring>(phase)
                assertContains(phase.scoringEvent.scoringPlayers, Players.green)
                assertIs<Road>(phase.scoringEvent.feature)

                assertEquals(0, scoreboard.getScore(Players.red))
                assertEquals(0, scoreboard.getScore(Players.green))

                assertIs<History.Event.TilePlacement>(history.events.last())

                assertEquals(listOf(greenFigure), board.figures.getValue(Coordinates(1, 0)))
                assertEquals(listOf(redFigure), board.figures.getValue(Coordinates(0, -1)))
            }

            // Wait for the first scoring to end, update scoreboard and history, and
            // immediately proceed to zoom on the red player scoring.
            with(awaitItem()) {
                assertIs<Phase.Scoring>(phase)
                assertContains(phase.scoringEvent.scoringPlayers, Players.red)
                assertIs<City>(phase.scoringEvent.feature)

                assertEquals(0, scoreboard.getScore(Players.red))
                assertEquals(4, scoreboard.getScore(Players.green))

                val event = history.events.last()
                assertIs<History.Event.Scoring>(event)
                assertEquals(1, event.board.figures.values.flatten().size)

                assertEquals(emptyList(), board.figures.getValue(Coordinates(1, 0)))
                assertEquals(listOf(redFigure), board.figures.getValue(Coordinates(0, -1)))
            }

            // Wait for the second scoring to end, update scoreboard and history, and
            // proceed with placing tile.
            with(awaitItem()) {
                assertIs<Phase.PlacingTile>(phase)

                assertEquals(6, scoreboard.getScore(Players.red))
                assertEquals(4, scoreboard.getScore(Players.green))

                val event = history.events.last()
                assertIs<History.Event.Scoring>(event)
                assertEquals(0, event.board.figures.values.flatten().size)

                assertEquals(emptyList(), board.figures.getValue(Coordinates(1, 0)))
                assertEquals(emptyList(), board.figures.getValue(Coordinates(0, -1)))
            }
        }
    }
}
