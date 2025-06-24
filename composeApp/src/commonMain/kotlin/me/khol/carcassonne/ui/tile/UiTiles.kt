package me.khol.carcassonne.ui.tile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import carcassonne.composeapp.generated.resources.*
import me.khol.carcassonne.tiles.Tiles
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.Tile
import me.khol.carcassonne.ui.Tile
import org.jetbrains.compose.ui.tooling.preview.Preview

object UiTiles {

    object Basic {

        val A = UiTile(drawable = Res.drawable.tile_basic_A, tile = Tiles.Basic.A, shapes = mapOf())
        val B = UiTile(drawable = Res.drawable.tile_basic_B, tile = Tiles.Basic.B, shapes = mapOf())
        val C = UiTile(drawable = Res.drawable.tile_basic_C, tile = Tiles.Basic.C, shapes = mapOf())
        val D = UiTile(drawable = Res.drawable.tile_basic_D, tile = Tiles.Basic.D, shapes = mapOf())
        val E = UiTile(drawable = Res.drawable.tile_basic_E, tile = Tiles.Basic.E, shapes = mapOf())
        val E_G = UiTile(drawable = Res.drawable.tile_basic_E, tile = Tiles.Basic.E_G, shapes = mapOf())
        val F = UiTile(drawable = Res.drawable.tile_basic_F, tile = Tiles.Basic.F, shapes = mapOf())
        val G = UiTile(drawable = Res.drawable.tile_basic_G, tile = Tiles.Basic.G, shapes = mapOf())
        val H = UiTile(drawable = Res.drawable.tile_basic_H, tile = Tiles.Basic.H, shapes = mapOf())
        val H_G = UiTile(drawable = Res.drawable.tile_basic_H, tile = Tiles.Basic.H_G, shapes = mapOf())
        val I = UiTile(drawable = Res.drawable.tile_basic_I, tile = Tiles.Basic.I, shapes = mapOf())
        val I_G = UiTile(drawable = Res.drawable.tile_basic_I, tile = Tiles.Basic.I_G, shapes = mapOf())
        val J = UiTile(drawable = Res.drawable.tile_basic_J, tile = Tiles.Basic.J, shapes = mapOf())
        val K = UiTile(drawable = Res.drawable.tile_basic_K, tile = Tiles.Basic.K, shapes = mapOf())
        val L = UiTile(drawable = Res.drawable.tile_basic_L, tile = Tiles.Basic.L, shapes = mapOf())
        val M = UiTile(drawable = Res.drawable.tile_basic_M, tile = Tiles.Basic.M, shapes = mapOf())
        val M_G = UiTile(drawable = Res.drawable.tile_basic_M, tile = Tiles.Basic.M_G, shapes = mapOf())
        val N = UiTile(drawable = Res.drawable.tile_basic_N, tile = Tiles.Basic.N, shapes = mapOf())
        val N_G = UiTile(drawable = Res.drawable.tile_basic_N, tile = Tiles.Basic.N_G, shapes = mapOf())
        val O = UiTile(drawable = Res.drawable.tile_basic_O, tile = Tiles.Basic.O, shapes = mapOf())
        val P = UiTile(drawable = Res.drawable.tile_basic_P, tile = Tiles.Basic.P, shapes = mapOf())
        val Q = UiTile(drawable = Res.drawable.tile_basic_Q, tile = Tiles.Basic.Q, shapes = mapOf())
        val R = UiTile(drawable = Res.drawable.tile_basic_R, tile = Tiles.Basic.R, shapes = mapOf())
        val R_G = UiTile(drawable = Res.drawable.tile_basic_R, tile = Tiles.Basic.R_G, shapes = mapOf())
        val S = UiTile(drawable = Res.drawable.tile_basic_S, tile = Tiles.Basic.S, shapes = mapOf())
        val T = UiTile(drawable = Res.drawable.tile_basic_T, tile = Tiles.Basic.T, shapes = mapOf())
        val U = UiTile(drawable = Res.drawable.tile_basic_U, tile = Tiles.Basic.U, shapes = mapOf())
        val U_G = UiTile(drawable = Res.drawable.tile_basic_U, tile = Tiles.Basic.U_G, shapes = mapOf())
        val V = UiTile(drawable = Res.drawable.tile_basic_V, tile = Tiles.Basic.V, shapes = mapOf())
        val V_G = UiTile(drawable = Res.drawable.tile_basic_V, tile = Tiles.Basic.V_G, shapes = mapOf())
        val W = UiTile(drawable = Res.drawable.tile_basic_W, tile = Tiles.Basic.W, shapes = mapOf())
        val X = UiTile(drawable = Res.drawable.tile_basic_X, tile = Tiles.Basic.X, shapes = mapOf())
    }
}

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
            setOf(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X)
        }
        all.chunked(6).forEach { chunk ->
            Row {
                chunk.forEach { tile ->
                    Tile(
                        drawable = tile.drawable,
                        rotation = Rotation.ROTATE_0,
                    )
                }
            }
        }
    }
}
