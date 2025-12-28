package me.khol.carcassonne.figure

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.ElementPosition
import me.khol.carcassonne.PlacedFigure
import me.khol.carcassonne.PlayerFigure
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.Tile
import me.khol.carcassonne.feature.placed
import me.khol.carcassonne.fixtures.PlayerFigures
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.basic.A
import me.khol.carcassonne.tiles.basic.C
import me.khol.carcassonne.tiles.basic.D
import me.khol.carcassonne.tiles.basic.V
import kotlin.test.Test

class OccupiedFeatureTest {

    private fun <E : Element<ElementPosition>> Board.placeGreenMeeple(
        x: Int,
        y: Int,
        tile: Tile,
        element: E,
        figure: PlayerFigure? = PlayerFigures.greenMeeple,
        rotation: Rotation = Rotation.ROTATE_0,
    ): Board {
        val coordinates = Coordinates(x, y)
        return placeTile(
            coordinates = coordinates,
            tile = tile.rotated(rotation),
            placedFigures = listOfNotNull(
                figure?.let {
                    PlacedFigure(
                        placedElement = element.rotate(rotation).placed(coordinates),
                        figure = it,
                    )
                }
            ),
        )
    }

    @Test
    fun `placing a meeple into an occupied city should fail`() {
        try {
            Board.starting(startingTile = D.tile)
                .placeGreenMeeple(0, -1, C.tile, C.city)
                .placeGreenMeeple(0, -2, C.tile, C.city)
            error("Placing the second meeple should fail")
        } catch (_: IllegalArgumentException) {
            // pass
        }
    }

    @Test
    fun `placing a meeple into an occupied road should fail`() {
        try {
            Board.starting(startingTile = D.tile)
                .placeGreenMeeple(1, 0, V.tile, V.road)
                .placeGreenMeeple(1, 1, V.tile, V.road, rotation = Rotation.ROTATE_90)
            error("Placing the second meeple should fail")
        } catch (_: IllegalArgumentException) {
            // pass
        }
    }

    @Test
    fun `placing a meeple into an occupied field should fail`() {
        try {
            Board.starting(startingTile = D.tile)
                .placeGreenMeeple(1, 0, D.tile, D.fieldTop)
                .placeGreenMeeple(-1, 0, D.tile, D.fieldTop)
            error("Placing the second meeple should fail")
        } catch (_: IllegalArgumentException) {
            // pass
        }
    }

    @Test
    fun `placing a meeple into an unoccupied city and then joining with another is okay`() {
        Board.starting(startingTile = D.tile)
            .placeGreenMeeple(0, -1, C.tile, C.city)
            .placeGreenMeeple(1, 0, D.tile, D.city)
            .placeGreenMeeple(0, -2, C.tile, C.city, figure = null)
    }

    @Test
    fun `placing a meeple into an unoccupied road and then joining with another is okay`() {
        Board.starting(startingTile = D.tile)
            .placeGreenMeeple(1, 0, V.tile, V.road)
            .placeGreenMeeple(0, 1, V.tile, V.road, rotation = Rotation.ROTATE_270)
            .placeGreenMeeple(1, 1, V.tile, V.road, rotation = Rotation.ROTATE_90, figure = null)
    }

    @Test
    fun `placing a meeple into an unoccupied field and then joining with another is okay`() {
        Board.starting(startingTile = D.tile)
            .placeGreenMeeple(1, 0, D.tile, D.fieldTop)
            .placeGreenMeeple(-1, 0, D.tile, D.fieldBottom)
            .placeGreenMeeple(2, 0, A.tile, A.road, rotation = Rotation.ROTATE_90, figure = null)
    }
}
