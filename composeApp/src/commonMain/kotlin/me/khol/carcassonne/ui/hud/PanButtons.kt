package me.khol.carcassonne.ui.hud

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun PanButtons(
    modifier: Modifier = Modifier,
    onPanLeftClick: () -> Unit,
    onPanRightClick: () -> Unit,
    onPanUpClick: () -> Unit,
    onPanDownClick: () -> Unit,
) {
    Surface(
        modifier = modifier.rotate(45f),
        shape = RoundedCornerShape(28.dp),
        elevation = 1.dp,
    ) {

        @Composable
        fun PanButton(
            onClick: () -> Unit,
            imageVector: ImageVector,
            contentDescription: String?,
        ) {
            TextButton(
                modifier = Modifier.size(48.dp),
                onClick = onClick,
            ) {
                Icon(imageVector, contentDescription, Modifier.rotate(-45f))
            }
        }

        Column(
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(24.dp)),
        ) {
            Row {
                PanButton(onPanUpClick, Icons.Default.KeyboardArrowUp, "Pan up")
                PanButton(onPanRightClick, Icons.Default.KeyboardArrowRight, "Pan right")
            }
            Row {
                PanButton(onPanLeftClick, Icons.Default.KeyboardArrowLeft, "Pan left")
                PanButton(onPanDownClick, Icons.Default.KeyboardArrowDown, "Pan down")
            }
        }
    }
}
