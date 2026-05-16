package me.khol.carcassonne.ui.hud

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import carcassonne.composeapp.generated.resources.Res
import carcassonne.composeapp.generated.resources.ic_meeple_outline
import carcassonne.composeapp.generated.resources.ic_star_outline
import me.khol.carcassonne.Game
import me.khol.carcassonne.figure.Meeple
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlayerListHud(
    game: Game,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(top = 16.dp)
            .width(IntrinsicSize.Max)
    ) {
        game.players.forEach { player ->
            val isActive = game.currentPlayer == player
            val playerColor = player.color.uiColor()
            val playerOnColor = player.color.uiOnColor()
            val elevation by animateDpAsState(if (isActive) 8.dp else 0.dp)
            val offset by animateDpAsState(if (isActive) (-4).dp else (-16).dp)
            val color by animateColorAsState(if (isActive) playerColor else MaterialTheme.colorScheme.surface)
            val contentColor by animateColorAsState(if (isActive) playerOnColor else MaterialTheme.colorScheme.onSurface)
            Surface(
                shape = RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50),
                shadowElevation = elevation,
                tonalElevation = elevation,
                color = color,
                contentColor = contentColor,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)),
                modifier = Modifier
                    .offset(x = offset)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .padding(start = 32.dp, end = 16.dp)
                ) {
                    val score = game.scoreboard.getScore(player)
                    val figureCounts = game.figureSupply.getCounts(player)
                    // TODO: Show other figure types too
                    val remainingMeeples = figureCounts.getValue(Meeple)
                    Text(
                        text = player.name,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_star_outline),
                            contentDescription = null,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "$score ",
                            maxLines = 1,
                            style = MaterialTheme.typography.labelMedium,
                        )
                        Icon(
                            painter = painterResource(Res.drawable.ic_meeple_outline),
                            contentDescription = null,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "$remainingMeeples",
                            maxLines = 1,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                }
            }
        }
    }
}
