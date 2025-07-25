package me.khol.carcassonne.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun GameSurface(
    content: @Composable BoxScope.() -> Unit,
) {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            Box {
                content()
            }
        }
    }
}
