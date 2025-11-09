package me.khol.carcassonne.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import me.khol.carcassonne.Engine
import me.khol.carcassonne.Game
import me.khol.carcassonne.History
import me.khol.carcassonne.fixtures.Players
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basicTileset
import me.khol.carcassonne.ui.hud.History
import me.khol.carcassonne.ui.hud.PhaseHud
import me.khol.carcassonne.ui.hud.PlayerListHud
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed interface Analysis {
    data object Off : Analysis
    data class On(
        val event: History.Event
    ) : Analysis
}

@Preview
@Composable
fun App() {
    MaterialTheme(
        colorScheme = lightColorScheme(
            surface = Color.White,
        )
    ) {
        val engine = remember {
            Engine(
                initialGame = Game.new(
                    tilesets = listOf(basicTileset),
                    startingTile = Tiles.Basic.D,
                    players = listOf(Players.green, Players.red),
                )
            )
        }
        val game by engine.game.collectAsState()

        var analysis by remember { mutableStateOf<Analysis>(Analysis.Off) }

        val background by animateColorAsState(
            when (analysis) {
                is Analysis.Off -> MaterialTheme.colorScheme.surface
                is Analysis.On -> MaterialTheme.colorScheme.surfaceVariant
            }
        )

        GameSurface(
            background = background,
        ) {
            GamePanningWindow(
                game = game,
            ) {
                when (val analysis = analysis) {
                    is Analysis.Off -> {
                        Board(
                            board = game.board,
                            phase = game.phase,
                            onPlaceTile = engine::placeTile,
                            onPlaceFigure = engine::placeFigure,
                        )
                    }
                    is Analysis.On -> {
                        SimpleBoard(
                            board = when (val event = analysis.event) {
                                is History.Event.TilePlacement -> event.board
                                is History.Event.Scoring -> event.board
                            },
                            modifier = Modifier
                        )
                    }
                }
            }

            var remainingTilesDialogOpen by remember { mutableStateOf(false) }

            if (remainingTilesDialogOpen) {
                Dialog(
                    onDismissRequest = { remainingTilesDialogOpen = false },
                ) {
                    RemainingTilesDialogContent(
                        allTiles = game.tiles,
                        remainingTiles = game.remainingTiles,
                    )
                }
            }

            PlayerListHud(
                game = game,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
            )

            AnimatedVisibility(
                visible = analysis is Analysis.Off,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
                modifier = Modifier
                    .align(alignment = Alignment.BottomStart)
            ) {
                PhaseHud(
                    phase = game.phase,
                    remainingTilesCount = game.remainingTiles.size,
                    confirmTilePlacement = engine::confirmTilePlacement,
                    confirmFigurePlacement = engine::confirmFigurePlacement,
                    onTilesLeftClick = { remainingTilesDialogOpen = true },
                    undo = engine::undo,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }

            AnimatedVisibility(
                visible = analysis is Analysis.On,
                enter = slideInHorizontally { it },
                exit = slideOutHorizontally { it },
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd)
            ) {
                History(
                    history = game.history,
                    onSelectedEventChange = { analysis = Analysis.On(it) },
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

            AnimatedVisibility(
                visible = game.history.events.isNotEmpty(),
                enter = slideInHorizontally { it },
                exit = slideOutHorizontally { it },
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
            ) {
                Button(
                    onClick = {
                        analysis = when (analysis) {
                            is Analysis.Off -> game.history.events.lastOrNull()
                                ?.let(Analysis::On) ?: Analysis.Off
                            is Analysis.On -> Analysis.Off
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text("History")
                }
            }
        }
    }
}
