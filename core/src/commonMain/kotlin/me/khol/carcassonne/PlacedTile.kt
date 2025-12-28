package me.khol.carcassonne

data class PlacedTile(
    val rotatedTile: RotatedTile,
    val coordinates: Coordinates,
)

fun RotatedTile.placed(coordinates: Coordinates) =
    PlacedTile(rotatedTile = this, coordinates = coordinates)

fun RotatedTile.placed(x: Int, y: Int) =
    placed(coordinates = Coordinates(x, y))
