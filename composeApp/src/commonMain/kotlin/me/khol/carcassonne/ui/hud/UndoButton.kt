package me.khol.carcassonne.ui.hud

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UndoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    @OptIn(ExperimentalMaterialApi::class)
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Undo,
            contentDescription = "Undo",
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun UndoButtonPreview() {
    MaterialTheme {
        Surface {
            UndoButton(
                onClick = {},
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}
