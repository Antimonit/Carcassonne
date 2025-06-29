package me.khol.carcassonne

import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basicTileset
import kotlin.test.Test
import kotlin.test.assertIs

class EngineTest {

    val engine = Engine(
        initialGame = Game.new(
            tilesets = listOf(basicTileset),
            startingTile = Tiles.Basic.D,
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
            tile = PlacedTile(
                rotatedTile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_0),
                coordinates = Coordinates(1, 0),
            )
        )
        assertIs<Phase.PlacingTile.Placed>(engine.game.value.phase)
        engine.undo()
        assertIs<Phase.PlacingTile.Fresh>(engine.game.value.phase)
    }

    @Test
    fun `undo placing a meeple`() {
        engine.placeFigure(
            tile = PlacedTile(
                rotatedTile = RotatedTile(Tiles.Basic.D, Rotation.ROTATE_0),
                coordinates = Coordinates(1, 0),
            ),
            element = me.khol.carcassonne.tiles.basic.D.road,
        )
        assertIs<Phase.PlacingFigure.Placed>(engine.game.value.phase)
        engine.undo()
        assertIs<Phase.PlacingFigure.Fresh>(engine.game.value.phase)
        engine.undo()
        assertIs<Phase.PlacingTile.Placed>(engine.game.value.phase)
        engine.undo()
        assertIs<Phase.PlacingTile.Fresh>(engine.game.value.phase)
    }
}
