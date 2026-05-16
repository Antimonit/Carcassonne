package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Element
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.placed
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
            .placeTile(BB6F10.rotated(Rotation.ROTATE_180).placed(0, -1), emptyList())

        val gardenFeatures = board.gardenFeatures
        assertEquals(1, gardenFeatures.size)

        val gardenFeature = gardenFeatures.first()
        assertEquals(
            expected = Garden(
                placedGarden = Element.Garden.rotated(Rotation.ROTATE_180).placed(0, -1),
                neighborCount = 2,
                figures = emptyList(),
            ),
            actual = gardenFeature,
        )
        assertFalse(gardenFeature.isFinished)
    }
}
