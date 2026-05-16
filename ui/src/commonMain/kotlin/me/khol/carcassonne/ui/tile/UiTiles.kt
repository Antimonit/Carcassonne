package me.khol.carcassonne.ui.tile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.khol.carcassonne.RotatedTile
import me.khol.carcassonne.Tile
import me.khol.carcassonne.ui.Tile

object UiTiles {

    object Basic {

        val A = me.khol.carcassonne.ui.tile.basic.A
        val B = me.khol.carcassonne.ui.tile.basic.B
        val C = me.khol.carcassonne.ui.tile.basic.C
        val D = me.khol.carcassonne.ui.tile.basic.D
        val E = me.khol.carcassonne.ui.tile.basic.E
        val E_G = me.khol.carcassonne.ui.tile.basic.E_G
        val F = me.khol.carcassonne.ui.tile.basic.F
        val G = me.khol.carcassonne.ui.tile.basic.G
        val H = me.khol.carcassonne.ui.tile.basic.H
        val H_G = me.khol.carcassonne.ui.tile.basic.H_G
        val I = me.khol.carcassonne.ui.tile.basic.I
        val I_G = me.khol.carcassonne.ui.tile.basic.I_G
        val J = me.khol.carcassonne.ui.tile.basic.J
        val K = me.khol.carcassonne.ui.tile.basic.K
        val L = me.khol.carcassonne.ui.tile.basic.L
        val M = me.khol.carcassonne.ui.tile.basic.M
        val M_G = me.khol.carcassonne.ui.tile.basic.M_G
        val N = me.khol.carcassonne.ui.tile.basic.N
        val N_G = me.khol.carcassonne.ui.tile.basic.N_G
        val O = me.khol.carcassonne.ui.tile.basic.O
        val P = me.khol.carcassonne.ui.tile.basic.P
        val Q = me.khol.carcassonne.ui.tile.basic.Q
        val R = me.khol.carcassonne.ui.tile.basic.R
        val R_G = me.khol.carcassonne.ui.tile.basic.R_G
        val S = me.khol.carcassonne.ui.tile.basic.S
        val T = me.khol.carcassonne.ui.tile.basic.T
        val U = me.khol.carcassonne.ui.tile.basic.U
        val U_G = me.khol.carcassonne.ui.tile.basic.U_G
        val V = me.khol.carcassonne.ui.tile.basic.V
        val V_G = me.khol.carcassonne.ui.tile.basic.V_G
        val W = me.khol.carcassonne.ui.tile.basic.W
        val X = me.khol.carcassonne.ui.tile.basic.X
    }
}

fun RotatedTile.toUiTile(): RotatedUiTile =
    RotatedUiTile(
        uiTile = tile.toUiTile(),
        rotation = rotation,
    )

fun Tile.toUiTile(): UiTile =
    with(UiTiles.Basic) {
        when (this@toUiTile) {
            me.khol.carcassonne.tiles.basic.A.tile -> UiTiles.Basic.A
            me.khol.carcassonne.tiles.basic.B.tile -> UiTiles.Basic.B
            me.khol.carcassonne.tiles.basic.C.tile -> UiTiles.Basic.C
            me.khol.carcassonne.tiles.basic.D.tile -> UiTiles.Basic.D
            me.khol.carcassonne.tiles.basic.E.tile -> UiTiles.Basic.E
            me.khol.carcassonne.tiles.basic.E_G.tile -> UiTiles.Basic.E_G
            me.khol.carcassonne.tiles.basic.F.tile -> UiTiles.Basic.F
            me.khol.carcassonne.tiles.basic.G.tile -> UiTiles.Basic.G
            me.khol.carcassonne.tiles.basic.H.tile -> UiTiles.Basic.H
            me.khol.carcassonne.tiles.basic.H_G.tile -> UiTiles.Basic.H_G
            me.khol.carcassonne.tiles.basic.I.tile -> UiTiles.Basic.I
            me.khol.carcassonne.tiles.basic.I_G.tile -> UiTiles.Basic.I_G
            me.khol.carcassonne.tiles.basic.J.tile -> UiTiles.Basic.J
            me.khol.carcassonne.tiles.basic.K.tile -> UiTiles.Basic.K
            me.khol.carcassonne.tiles.basic.L.tile -> UiTiles.Basic.L
            me.khol.carcassonne.tiles.basic.M.tile -> UiTiles.Basic.M
            me.khol.carcassonne.tiles.basic.M_G.tile -> UiTiles.Basic.M_G
            me.khol.carcassonne.tiles.basic.N.tile -> UiTiles.Basic.N
            me.khol.carcassonne.tiles.basic.N_G.tile -> UiTiles.Basic.N_G
            me.khol.carcassonne.tiles.basic.O.tile -> UiTiles.Basic.O
            me.khol.carcassonne.tiles.basic.P.tile -> UiTiles.Basic.P
            me.khol.carcassonne.tiles.basic.Q.tile -> UiTiles.Basic.Q
            me.khol.carcassonne.tiles.basic.R.tile -> UiTiles.Basic.R
            me.khol.carcassonne.tiles.basic.R_G.tile -> UiTiles.Basic.R_G
            me.khol.carcassonne.tiles.basic.S.tile -> UiTiles.Basic.S
            me.khol.carcassonne.tiles.basic.T.tile -> UiTiles.Basic.T
            me.khol.carcassonne.tiles.basic.U.tile -> UiTiles.Basic.U
            me.khol.carcassonne.tiles.basic.U_G.tile -> UiTiles.Basic.U_G
            me.khol.carcassonne.tiles.basic.V.tile -> UiTiles.Basic.V
            me.khol.carcassonne.tiles.basic.V_G.tile -> UiTiles.Basic.V_G
            me.khol.carcassonne.tiles.basic.W.tile -> UiTiles.Basic.W
            me.khol.carcassonne.tiles.basic.X.tile -> UiTiles.Basic.X
            else -> error("Unknown tile $this")
        }
    }

@Preview
@Composable
private fun AllBasicTilesPreview() {
    Column {
        val all = with(UiTiles.Basic) {
            setOf(A, B, C, D, E, E_G, F, G, H, H_G, I, I_G, J, K, L, M, M_G, N, N_G, O, P, Q, R, R_G, S, T, U, U_G, V, V_G, W, X)
        }
        all.chunked(6).forEach { chunk ->
            Row {
                chunk.forEach { tile ->
                    Tile(
                        tile = tile,
                    )
                }
            }
        }
    }
}
