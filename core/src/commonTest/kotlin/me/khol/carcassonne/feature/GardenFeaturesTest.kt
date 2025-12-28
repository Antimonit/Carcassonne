package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.rotated
import me.khol.carcassonne.tiles.river.BB6F1
import me.khol.carcassonne.tiles.river.BB6F10
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class GardenFeaturesTest {

    @Test
    fun `garden feature`() {
        val board = Board.starting(startingTile = BB6F1)
            .placeTile(Coordinates(0, -1), BB6F10.rotated(Rotation.ROTATE_180), emptyList())

        val gardenFeatures = board.getGardenFeatures()
        assertEquals(1, gardenFeatures.size)

        val gardenFeature = gardenFeatures.first()
        assertEquals(
            expected = Feature.Garden(
                placedGarden = PlacedGarden(Coordinates(0, -1), Element.Garden),
                neighborCount = 2,
                figures = emptyList(),
            ),
            actual = gardenFeature,
        )
        assertFalse(gardenFeature.isFinished)
    }
}
