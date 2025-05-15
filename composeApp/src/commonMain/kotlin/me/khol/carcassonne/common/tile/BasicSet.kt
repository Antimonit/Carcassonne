package me.khol.carcassonne.common.tile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import carcassonne.composeapp.generated.resources.*
import me.khol.carcassonne.common.GridPosition
import me.khol.carcassonne.common.HiResTile
import me.khol.carcassonne.common.PlacedTile
import me.khol.carcassonne.common.Tile
import org.jetbrains.compose.ui.tooling.preview.Preview

object BasicSet {
    val A = HiResTile(drawable = Res.drawable.Carcassonne_A)
    val B = HiResTile(drawable = Res.drawable.Carcassonne_B)
    val C = HiResTile(drawable = Res.drawable.Carcassonne_C)
    val D = HiResTile(drawable = Res.drawable.Carcassonne_D)
    val E = HiResTile(drawable = Res.drawable.Carcassonne_E)
    val F = HiResTile(drawable = Res.drawable.Carcassonne_F)
    val G = HiResTile(drawable = Res.drawable.Carcassonne_G)
    val H = HiResTile(drawable = Res.drawable.Carcassonne_H)
    val I = HiResTile(drawable = Res.drawable.Carcassonne_I)
    val J = HiResTile(drawable = Res.drawable.Carcassonne_J)
    val K = HiResTile(drawable = Res.drawable.Carcassonne_K)
    val M = HiResTile(drawable = Res.drawable.Carcassonne_M)
    val N = HiResTile(drawable = Res.drawable.Carcassonne_N)
    val O = HiResTile(drawable = Res.drawable.Carcassonne_O)
    val P = HiResTile(drawable = Res.drawable.Carcassonne_P)
    val Q = HiResTile(drawable = Res.drawable.Carcassonne_Q)
    val R = HiResTile(drawable = Res.drawable.Carcassonne_R)
    val S = HiResTile(drawable = Res.drawable.Carcassonne_S)
    val T = HiResTile(drawable = Res.drawable.Carcassonne_T)
    val U = HiResTile(drawable = Res.drawable.Carcassonne_U)
    val V = HiResTile(drawable = Res.drawable.Carcassonne_V)
    val W = HiResTile(drawable = Res.drawable.Carcassonne_W)
    val X = HiResTile(drawable = Res.drawable.Carcassonne_X)
    val Y = HiResTile(drawable = Res.drawable.Carcassonne_Y)

    val all = setOf(A, B, C, D, E, F, G, H, I, J, K, M, N, O, P, Q, R, S, T, U, V, W, X, Y)
}

@Preview
@Composable
fun BasicSetPreview() {
    Column {
        BasicSet.all.chunked(6).forEach { chunk ->
            Row {
                chunk.forEach { tile ->
                    Tile(
                        placedTile = PlacedTile(
                            position = GridPosition(x = 0, y = 0),
                            tile = tile,
                            rotation = Rotation.ROTATE_0
                        )
                    )
                }
            }
        }
    }
}
