package me.khol.carcassonne.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.zIndex

fun Modifier.zIndexOnHover(): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val zIndex by animateFloatAsState(if (isHovered) 2f else 1f)
    Modifier
        .hoverable(interactionSource = interactionSource)
        .zIndex(zIndex)
}
