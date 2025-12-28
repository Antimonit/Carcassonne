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
import me.khol.carcassonne.tiles.Tiles
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
                        placedElement = element.rotated(rotation).placed(coordinates),
                        figure = it,
                    )
                }
            ),
        )
    }

    @Test
    fun `placing a meeple into an occupied city should fail`() {
        try {
            Board.starting(startingTile = Tiles.Basic.D.tile)
                .placeGreenMeeple(0, -1, Tiles.Basic.C.tile, Tiles.Basic.C.city)
                .placeGreenMeeple(0, -2, Tiles.Basic.C.tile, Tiles.Basic.C.city)
            error("Placing the second meeple should fail")
        } catch (_: IllegalArgumentException) {
            // pass
        }
    }

    @Test
    fun `placing a meeple into an occupied road should fail`() {
        try {
            Board.starting(startingTile = Tiles.Basic.D.tile)
                .placeGreenMeeple(1, 0, Tiles.Basic.V.tile, Tiles.Basic.V.road)
                .placeGreenMeeple(1, 1, Tiles.Basic.V.tile, Tiles.Basic.V.road, rotation = Rotation.ROTATE_90)
            error("Placing the second meeple should fail")
        } catch (_: IllegalArgumentException) {
            // pass
        }
    }

    @Test
    fun `placing a meeple into an occupied field should fail`() {
        try {
            Board.starting(startingTile = Tiles.Basic.D.tile)
                .placeGreenMeeple(1, 0, Tiles.Basic.D.tile, Tiles.Basic.D.fieldTop)
                .placeGreenMeeple(-1, 0, Tiles.Basic.D.tile, Tiles.Basic.D.fieldTop)
            error("Placing the second meeple should fail")
        } catch (_: IllegalArgumentException) {
            // pass
        }
    }

    @Test
    fun `placing a meeple into an unoccupied city and then joining with another is okay`() {
        Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeGreenMeeple(0, -1, Tiles.Basic.C.tile, Tiles.Basic.C.city)
            .placeGreenMeeple(1, 0, Tiles.Basic.D.tile, Tiles.Basic.D.city)
            .placeGreenMeeple(0, -2, Tiles.Basic.C.tile, Tiles.Basic.C.city, figure = null)
    }

    @Test
    fun `placing a meeple into an unoccupied road and then joining with another is okay`() {
        Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeGreenMeeple(1, 0, Tiles.Basic.V.tile, Tiles.Basic.V.road)
            .placeGreenMeeple(0, 1, Tiles.Basic.V.tile, Tiles.Basic.V.road, rotation = Rotation.ROTATE_270)
            .placeGreenMeeple(1, 1, Tiles.Basic.V.tile, Tiles.Basic.V.road, rotation = Rotation.ROTATE_90, figure = null)
    }

    @Test
    fun `placing a meeple into an unoccupied field and then joining with another is okay`() {
        Board.starting(startingTile = Tiles.Basic.D.tile)
            .placeGreenMeeple(1, 0, Tiles.Basic.D.tile, Tiles.Basic.D.fieldTop)
            .placeGreenMeeple(-1, 0, Tiles.Basic.D.tile, Tiles.Basic.D.fieldBottom)
            .placeGreenMeeple(2, 0, Tiles.Basic.A.tile, Tiles.Basic.A.road, rotation = Rotation.ROTATE_90, figure = null)
    }
}
