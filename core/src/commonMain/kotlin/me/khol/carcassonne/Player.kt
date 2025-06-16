package me.khol.carcassonne

data class Player(
    val name: String,
    val color: Color,
) {
    enum class Color {
        Black,
        Blue,
        Green,
        Pink,
        Red,
        Yellow,
    }
}