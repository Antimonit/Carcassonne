package me.khol.carcassonne

import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.figure.Abbot
import me.khol.carcassonne.figure.Meeple
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.fixtures.Players
import me.khol.carcassonne.tiles.Tiles
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidFigurePlacementsTest {

    @Test
    fun `valid figure placements`() {
        val board = Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeTile(
                coordinates = Coordinates(1, 0),
                tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180),
                placedFigures = listOf(
                    PlacedFigure(
                        placedElement = Tiles.Basic.D.fieldTop.rotated(Rotation.ROTATE_180).placed(1, 0),
                        figure = PlayerFigures.greenMeeple,
                    ),
                ),
            )

        val placements = board.validFigurePlacements(
            placedTile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_180).placed(-1, 0),
            currentPlayer = Players.green,
            figureSupply = FigureSupply(remainingFigureCount = mapOf(Players.green to mapOf(Meeple to 7)))
        )

        assertEquals(
            expected = mapOf(
                Tiles.Basic.D.fieldTop.rotated(Rotation.ROTATE_180) to emptyList(),
                Tiles.Basic.D.fieldBottom.rotated(Rotation.ROTATE_180) to listOf(
                    PlacedFigure(
                        placedElement = Tiles.Basic.D.fieldBottom.rotated(Rotation.ROTATE_180).placed(-1, 0),
                        figure = PlayerFigures.greenMeeple,
                    ),
                ),
                Tiles.Basic.D.road.rotated(Rotation.ROTATE_180) to listOf(
                    PlacedFigure(
                        placedElement = Tiles.Basic.D.road.rotated(Rotation.ROTATE_180).placed(-1, 0),
                        figure = PlayerFigures.greenMeeple,
                    ),
                ),
                Tiles.Basic.D.city.rotated(Rotation.ROTATE_180) to listOf(
                    PlacedFigure(
                        placedElement = Tiles.Basic.D.city.rotated(Rotation.ROTATE_180).placed(-1, 0),
                        figure = PlayerFigures.greenMeeple,
                    ),
                ),
            ),
            actual = placements,
        )
    }

    @Test
    fun `no more than 7 meeples can be placed by a single player`() {
        var board = Board.starting(startingTile = Tiles.Basic.D.tile)

        repeat(7) { x ->
            val coordinates = Coordinates(x + 1, 0)
            board = board.placeTile(
                coordinates = coordinates,
                tile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_0),
                placedFigures = listOf(
                    PlacedFigure(
                        placedElement = Tiles.Basic.D.city.rotated(Rotation.ROTATE_0).placed(coordinates),
                        figure = PlayerFigures.greenMeeple,
                    ),
                ),
            )
        }

        val placements = board.validFigurePlacements(
            placedTile = Tiles.Basic.D.tile.rotated(Rotation.ROTATE_0).placed(8, 0),
            currentPlayer = Players.green,
            figureSupply = board.figureSupply(
                players = listOf(Players.green),
                maxFigureCounts = mapOf(Meeple to 7, Abbot to 1),
            ),
        )

        assertEquals(
            expected = mapOf(
                Tiles.Basic.D.fieldTop.rotated(Rotation.ROTATE_0) to emptyList(),
                Tiles.Basic.D.fieldBottom.rotated(Rotation.ROTATE_0) to emptyList(),
                Tiles.Basic.D.road.rotated(Rotation.ROTATE_0) to emptyList(),
                Tiles.Basic.D.city.rotated(Rotation.ROTATE_0) to emptyList(),
            ),
            actual = placements,
        )
    }
}
