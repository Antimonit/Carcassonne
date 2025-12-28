package me.khol.carcassonne

import me.khol.carcassonne.feature.PlacedElement
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.fixtures.Players
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basicTileset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class EngineTest {

    val playerRed = Players.red
    val playerGreen = Players.green
    val engine = Engine(
        initialGame = Game.new(
            tilesets = listOf(basicTileset),
            startingTile = Tiles.Basic.D,
            players = listOf(playerRed, playerGreen)
        ),
    )

    @Test
    fun `undo placing an unplaced tile has no effect`() {
        assertIs<Phase.PlacingTile.Fresh>(engine.game.value.phase)
        engine.undo()
        assertIs<Phase.PlacingTile.Fresh>(engine.game.value.phase)
    }

    @Test
    fun `undo placing a placed tile`() {
        engine.placeTile(
            tile = Tiles.Basic.D.rotated(Rotation.ROTATE_0).placed(Coordinates(1, 0))
        )
        assertIs<Phase.PlacingTile.Placed>(engine.game.value.phase)
        engine.undo()
        assertIs<Phase.PlacingTile.Fresh>(engine.game.value.phase)
    }

    @Test
    fun `undo placing a meeple`() {
        engine.placeFigure(
            tile = Tiles.Basic.D.rotated(Rotation.ROTATE_0).placed(Coordinates(1, 0)),
            placedFigure = PlacedFigure(
                PlacedElement(
                    coordinates = Coordinates(1, 0),
                    element = me.khol.carcassonne.tiles.basic.D.road,
                ),
                figure = PlayerFigures.greenMeeple,
            )
        )
        assertIs<Phase.PlacingFigure.Placed>(engine.game.value.phase)
        engine.undo()
        assertIs<Phase.PlacingFigure.Fresh>(engine.game.value.phase)
        engine.undo()
        assertIs<Phase.PlacingTile.Placed>(engine.game.value.phase)
        engine.undo()
        assertIs<Phase.PlacingTile.Fresh>(engine.game.value.phase)
    }

    @Test
    fun `placing a tile changes the current player`() {
        assertEquals(playerRed, engine.game.value.currentPlayer)
        engine.confirmFigurePlacement(
            phase = Phase.PlacingFigure.Fresh(
                placedTile = Tiles.Basic.D.rotated(Rotation.ROTATE_0).placed(Coordinates(1, 0)),
                validFigurePlacements = Tiles.Basic.D.elements.all().associateWith { emptyList() },
            )
        )
        assertEquals(playerGreen, engine.game.value.currentPlayer)
        engine.confirmFigurePlacement(
            phase = Phase.PlacingFigure.Fresh(
                placedTile = Tiles.Basic.D.rotated(Rotation.ROTATE_0).placed(Coordinates(2, 0)),
                validFigurePlacements = Tiles.Basic.D.elements.all().associateWith { emptyList() },
            )
        )
        assertEquals(playerRed, engine.game.value.currentPlayer)
    }
}
