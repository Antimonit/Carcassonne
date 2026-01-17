package me.khol.carcassonne.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp
import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.cross
import me.khol.carcassonne.Game
import me.khol.carcassonne.ui.tile.tileSize
import me.khol.carcassonne.ui.tile.tileSpacing
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun RemainingBoardSpaceCountsHints(game: Game) {
    GridLayout(
        cellSize = tileSize,
        cellSpacing = tileSpacing,
    ) {
        if (game.remainingTiles.isNotEmpty()) {
            val counts = remember(game) { game.remainingBoardSpaceCounts() }
            counts.forEach { (coordinates, size) ->
                key(coordinates) {
                    Box(
                        propagateMinConstraints = true,
                        modifier = Modifier.coordinates(coordinates)
                    ) {
                        when (size) {
                            0 -> UnavailableSpace()
                            in 1..3 -> LimitedAvailabilitySpace(
                                remainingPossibilities = size,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UnavailableSpace(
    modifier: Modifier = Modifier
) {
    val tint = Color.Red.copy(alpha = 0.5f).compositeOver(Color.White)
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(16.dp)
            .border(2.dp, tint, RoundedCornerShape(8.dp))
            .background(tint.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
    ) {
        Icon(
            painter = painterResource(Res.drawable.cross),
            tint = tint,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun LimitedAvailabilitySpace(
    remainingPossibilities: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        val tint = Color(0xFFBBBBBB)
        repeat(remainingPossibilities) { index ->
            val offset = ((index - (remainingPossibilities - 1) / 2f) * 8).dp
            Box(
                modifier = Modifier
                    .offset(-offset, offset)
                    .size(24.dp)
                    .border(2.dp, tint, RoundedCornerShape(4.dp))
                    .background(
                        tint.copy(alpha = 0.25f).compositeOver(Color.White),
                        RoundedCornerShape(4.dp)
                    )
            )
        }
    }
}

@Preview
@Composable
private fun UnavailableSpacePreview() {
    MaterialTheme {
        Surface {
            UnavailableSpace(
                modifier = Modifier.size(tileSize)
            )
        }
    }
}

private class RemainingPossibilitiesParameterProvider : PreviewParameterProvider<Int> {
    override val values = sequenceOf(1, 2, 3)
}

@Preview
@Composable
private fun LimitedAvailabilitySpacePreview(
    @PreviewParameter(RemainingPossibilitiesParameterProvider::class)
    remainingPossibilities: Int,
) {
    MaterialTheme {
        Surface {
            LimitedAvailabilitySpace(
                remainingPossibilities = remainingPossibilities,
                modifier = Modifier.size(tileSize)
            )
        }
    }
}
