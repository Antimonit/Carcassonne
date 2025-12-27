package me.khol.carcassonne

import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.figure.Abbot
import me.khol.carcassonne.figure.Meeple
import me.khol.carcassonne.fixtures.Players
import me.khol.carcassonne.tiles.basic.A
import me.khol.carcassonne.tiles.basic.D
import kotlin.test.Test
import kotlin.test.assertEquals

class FigureSupplyTest {

    private val board = Board.starting(D.tile)

    @Test
    fun `all figures available at the start`() {
        val supply = board
            .figureSupply(
                players = listOf(Players.green, Players.red),
                maxFigureCounts = mapOf(Meeple to 7, Abbot to 1),
            )
        assertEquals(7, supply.getCounts(Players.green)[Meeple])
        assertEquals(1, supply.getCounts(Players.green)[Abbot])
        assertEquals(7, supply.getCounts(Players.red)[Meeple])
        assertEquals(1, supply.getCounts(Players.red)[Abbot])
    }

    @Test
    fun `less figures available after placing them`() {
        val supply = board
            .placeTile(
                coordinates = Coordinates(1, 0),
                tile = RotatedTile(
                    tile = A.tile,
                    rotation = Rotation.ROTATE_90,
                ),
                placedFigures = listOf(
                    PlacedFigure(
                        placedElement = A.road.rotated(Rotation.ROTATE_90).placed(1, 0),
                        figure = PlayerFigure(
                            figure = Meeple,
                            player = Players.green,
                        )
                    )
                )
            )
            .placeTile(
                coordinates = Coordinates(-1, 0),
                tile = RotatedTile(
                    tile = A.tile,
                    rotation = Rotation.ROTATE_270,
                ),
                placedFigures = listOf(
                    PlacedFigure(
                        placedElement = A.monastery.rotated(Rotation.ROTATE_270).placed(-1, 0),
                        figure = PlayerFigure(
                            figure = Abbot,
                            player = Players.red,
                        )
                    )
                )
            )
            .figureSupply(
                players = listOf(Players.green, Players.red),
                maxFigureCounts = mapOf(Meeple to 7, Abbot to 1),
            )
        assertEquals(6, supply.getCounts(Players.green)[Meeple])
        assertEquals(1, supply.getCounts(Players.green)[Abbot])
        assertEquals(7, supply.getCounts(Players.red)[Meeple])
        assertEquals(0, supply.getCounts(Players.red)[Abbot])
    }
}
