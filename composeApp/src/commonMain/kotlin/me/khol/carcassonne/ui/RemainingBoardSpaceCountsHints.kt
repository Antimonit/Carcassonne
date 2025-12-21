package me.khol.carcassonne.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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

@Composable
fun RemainingBoardSpaceCountsHints(game: Game) {
    GridLayout(
        cellSize = tileSize,
        cellSpacing = tileSpacing,
    ) {
        val counts = remember(game) { game.remainingBoardSpaceCounts() }
        // For tile spaces that have no possible tiles left, we display a red square
        // with a cross in the middle.
        if (game.remainingTiles.isNotEmpty()) {
            counts
                .filterValues { it == 0 }
                .forEach { (coordinates, _) ->
                    key(coordinates) {
                        val tint = Color.Red.copy(alpha = 0.5f).compositeOver(Color.White)
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(16.dp)
                                .border(2.dp, tint, RoundedCornerShape(8.dp))
                                .background(tint.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .coordinates(coordinates)
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.cross),
                                tint = tint,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
        }
        // For tile spaces with only a few remaining tiles left, we display a stack
        // of small gray tiles representing the remaining count.
        counts
            .filterValues { it in 1..3 }
            .forEach { (coordinates, size) ->
                key(coordinates) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .coordinates(coordinates),
                    ) {
                        val tint = Color(0xFFBBBBBB)
                        repeat(size) { index ->
                            val offset = ((index - (size - 1) / 2f) * 8).dp
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
            }
    }
}
