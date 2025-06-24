package me.khol.carcassonne.ui.tile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import carcassonne.composeapp.generated.resources.*
import me.khol.carcassonne.Rotation
import me.khol.carcassonne.Tile
import me.khol.carcassonne.ui.Tile
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.ui.tooling.preview.Preview

object Tiles {

    object Basic {
        val A = Res.drawable.tile_basic_A
        val B = Res.drawable.tile_basic_B
        val C = Res.drawable.tile_basic_C
        val D = Res.drawable.tile_basic_D
        val E = Res.drawable.tile_basic_E
        val F = Res.drawable.tile_basic_F
        val G = Res.drawable.tile_basic_G
        val H = Res.drawable.tile_basic_H
        val I = Res.drawable.tile_basic_I
        val J = Res.drawable.tile_basic_J
        val K = Res.drawable.tile_basic_K
        val L = Res.drawable.tile_basic_L
        val M = Res.drawable.tile_basic_M
        val N = Res.drawable.tile_basic_N
        val O = Res.drawable.tile_basic_O
        val P = Res.drawable.tile_basic_P
        val Q = Res.drawable.tile_basic_Q
        val R = Res.drawable.tile_basic_R
        val S = Res.drawable.tile_basic_S
        val T = Res.drawable.tile_basic_T
        val U = Res.drawable.tile_basic_U
        val V = Res.drawable.tile_basic_V
        val W = Res.drawable.tile_basic_W
        val X = Res.drawable.tile_basic_X

        val all = setOf(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X)
    }
}

fun Tile.toDrawable(): DrawableResource =
    with(Tiles.Basic) {
        when (this@toDrawable) {
            me.khol.carcassonne.tiles.basic.A -> A
            me.khol.carcassonne.tiles.basic.B -> B
            me.khol.carcassonne.tiles.basic.C -> C
            me.khol.carcassonne.tiles.basic.D -> D
            me.khol.carcassonne.tiles.basic.E -> E
            me.khol.carcassonne.tiles.basic.E_G -> E
            me.khol.carcassonne.tiles.basic.F -> F
            me.khol.carcassonne.tiles.basic.G -> G
            me.khol.carcassonne.tiles.basic.H -> H
            me.khol.carcassonne.tiles.basic.H_G -> H
            me.khol.carcassonne.tiles.basic.I -> I
            me.khol.carcassonne.tiles.basic.I_G -> I
            me.khol.carcassonne.tiles.basic.J -> J
            me.khol.carcassonne.tiles.basic.K -> K
            me.khol.carcassonne.tiles.basic.L -> L
            me.khol.carcassonne.tiles.basic.M -> M
            me.khol.carcassonne.tiles.basic.M_G -> M
            me.khol.carcassonne.tiles.basic.N -> N
            me.khol.carcassonne.tiles.basic.N_G -> N
            me.khol.carcassonne.tiles.basic.O -> O
            me.khol.carcassonne.tiles.basic.P -> P
            me.khol.carcassonne.tiles.basic.Q -> Q
            me.khol.carcassonne.tiles.basic.R -> R
            me.khol.carcassonne.tiles.basic.R_G -> R
            me.khol.carcassonne.tiles.basic.S -> S
            me.khol.carcassonne.tiles.basic.T -> T
            me.khol.carcassonne.tiles.basic.U -> U
            me.khol.carcassonne.tiles.basic.U_G -> U
            me.khol.carcassonne.tiles.basic.V -> V
            me.khol.carcassonne.tiles.basic.V_G -> V
            me.khol.carcassonne.tiles.basic.W -> W
            me.khol.carcassonne.tiles.basic.X -> X
            else -> error("Unknown tile $this")
        }
    }

@Preview
@Composable
private fun AllBasicTilesPreview() {
    Column {
        Tiles.Basic.all.chunked(6).forEach { chunk ->
            Row {
                chunk.forEach { tile ->
                    Tile(
                        drawable = tile,
                        rotation = Rotation.ROTATE_0,
                    )
                }
            }
        }
    }
}
