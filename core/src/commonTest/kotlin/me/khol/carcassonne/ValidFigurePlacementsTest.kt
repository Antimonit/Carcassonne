package me.khol.carcassonne

import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.fixtures.Players
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidFigurePlacementsTest {

    @Test
    fun `valid figure placements`() {
        val d = me.khol.carcassonne.tiles.basic.D
        val board = Board.starting(startingTile = d.tile)
            .placeTile(
                coordinates = Coordinates(1, 0),
                tile = d.tile.rotated(Rotation.ROTATE_180),
                placedFigures = listOf(
                    PlacedFigure(
                        placedElement = d.fieldTop.rotate(Rotation.ROTATE_180).placed(Coordinates(1, 0)),
                        figure = PlayerFigures.greenMeeple,
                    ),
                ),
            )

        val placements = board.validFigurePlacements(
            placedTile = d.tile.rotated(Rotation.ROTATE_180).placed(-1, 0),
            currentPlayer = Players.green,
        )

        assertEquals(
            expected = mapOf(
                d.fieldTop.rotate(Rotation.ROTATE_180) to emptyList(),
                d.fieldBottom.rotate(Rotation.ROTATE_180) to listOf(
                    PlacedFigure(
                        placedElement = d.fieldBottom.rotate(Rotation.ROTATE_180).placed(Coordinates(-1, 0)),
                        figure = PlayerFigures.greenMeeple,
                    ),
                ),
                d.road.rotate(Rotation.ROTATE_180) to listOf(
                    PlacedFigure(
                        placedElement = d.road.rotate(Rotation.ROTATE_180).placed(Coordinates(-1, 0)),
                        figure = PlayerFigures.greenMeeple,
                    ),
                ),
                d.city.rotate(Rotation.ROTATE_180) to listOf(
                    PlacedFigure(
                        placedElement = d.city.rotate(Rotation.ROTATE_180).placed(Coordinates(-1, 0)),
                        figure = PlayerFigures.greenMeeple,
                    ),
                ),
            ),
            actual = placements,
        )
    }
}
