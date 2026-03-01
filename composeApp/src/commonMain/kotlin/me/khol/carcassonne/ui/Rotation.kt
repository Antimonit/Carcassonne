package me.khol.carcassonne.ui

import me.khol.carcassonne.Rotation

val Rotation.degrees: Float
    get() = when (this) {
        Rotation.ROTATE_0 -> 0f
        Rotation.ROTATE_90 -> 90f
        Rotation.ROTATE_180 -> 180f
        Rotation.ROTATE_270 -> 270f
    }
