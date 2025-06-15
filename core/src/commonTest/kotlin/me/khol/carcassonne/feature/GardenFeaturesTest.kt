package me.khol.carcassonne.feature

import me.khol.carcassonne.Board
import me.khol.carcassonne.Coordinates
import me.khol.carcassonne.Element
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.tiles.river.RiverA
import me.khol.carcassonne.tiles.river.RiverJ
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class GardenFeaturesTest {

    @Test
    fun `garden feature`() {
        val board = Board.starting(startingTile = RiverA)
            .placeTile(Coordinates(0, -1), RotatedTile(RiverJ, Rotation.ROTATE_180), emptyList())

        val gardenFeatures = board.getGardenFeatures()
        assertEquals(1, gardenFeatures.size)

        val gardenFeature = gardenFeatures.first()
        assertEquals(
            expected = Feature.Garden(
                placedGarden = PlacedGarden(Coordinates(0, -1), Element.Garden),
                neighborCount = 2,
            ),
            actual = gardenFeature,
        )
        assertFalse(gardenFeature.isFinished)
    }
}
