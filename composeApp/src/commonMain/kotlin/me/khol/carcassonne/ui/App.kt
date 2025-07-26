package me.khol.carcassonne.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.khol.carcassonne.Engine
import me.khol.carcassonne.Game
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.tiles.basicTileset
import me.khol.carcassonne.ui.hud.History
import me.khol.carcassonne.ui.hud.PhaseHud
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    val engine = remember {
        Engine(
            initialGame = Game.new(
                tilesets = listOf(basicTileset),
                startingTile = Tiles.Basic.D,
            )
        )
    }
    val game by engine.game.collectAsState()

    GameSurface {
        PanningWindow {
            Board(
                board = game.board,
                phase = game.phase,
                onPlaceTile = engine::placeTile,
                onPlaceFigure = engine::placeFigure,
            )
        }

        PhaseHud(
            phase = game.phase,
            remainingTilesCount = game.remainingTiles.size,
            confirmTilePlacement = engine::confirmTilePlacement,
            confirmFigurePlacement = engine::confirmFigurePlacement,
            undo = engine::undo,
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.BottomStart)
        )

        History(
            history = game.history,
            modifier = Modifier
                .padding(8.dp)
                .align(alignment = Alignment.CenterEnd)
        )
    }
}
