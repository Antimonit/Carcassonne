package me.khol.carcassonne

data class Board(
    val tiles: Map<Coordinates, RotatedTile>,
    val figures: Map<Coordinates, List<PlacedFigure>>,
) {

    val openSpaces: Set<Coordinates> = tiles.keys
        .flatMap { it.neighbors }
        .plus(Coordinates(0, 0))
        .distinct()
        .minus(tiles.keys)
        .toSet()

    companion object {
        val empty = Board(
            tiles = emptyMap(),
            figures = emptyMap(),
        )

        fun starting(
            startingTile: Tile,
        ) = empty.placeTile(
            coordinates = Coordinates(0, 0),
            tile = RotatedTile(startingTile, Rotation.ROTATE_0),
            placedFigures = emptyList(),
        )
    }

    fun getTile(coordinates: Coordinates): RotatedTile? = tiles[coordinates]

    fun getFigures(coordinates: Coordinates): List<PlacedFigure> = figures[coordinates] ?: emptyList()

    fun placeTile(coordinates: Coordinates, tile: RotatedTile, placedFigures: List<PlacedFigure>): Board {
        require(coordinates !in tiles.keys) {
            "Cannot place tile ${tile.tile.name} at $coordinates as another tile is already placed there."
        }
        require(coordinates in openSpaces) {
            "Cannot place tile ${tile.tile.name} at $coordinates as it is not connected to the rest of the board."
        }
        require(PlacedTile(tile, coordinates) in possibleSpacesForTile(tile.tile).getValue(coordinates)) {
            "Cannot place tile ${tile.tile.name} at $coordinates as it does not match edges with one or more neighbors."
        }

        return Board(
            tiles = tiles + (coordinates to tile),
            figures = figures + mapOf(coordinates to placedFigures),
        )
    }

    fun possibleSpacesForTile(tile: Tile): Map<Coordinates, List<PlacedTile>> = buildMap {
        val rotatedTiles: List<RotatedTile> = Rotation.entries.map { rotation ->
            RotatedTile(tile, rotation)
        }
        openSpaces.forEach { centerSpace ->
            val top = getTile(centerSpace.top)?.edges?.bottom
            val right = getTile(centerSpace.right)?.edges?.left
            val bottom = getTile(centerSpace.bottom)?.edges?.top
            val left = getTile(centerSpace.left)?.edges?.right

            val satisfiedRotations = rotatedTiles.filter { rotatedTile ->
                listOfNotNull(
                    top?.let { rotatedTile.edges.top == it },
                    bottom?.let { rotatedTile.edges.bottom == it },
                    left?.let { rotatedTile.edges.left == it },
                    right?.let { rotatedTile.edges.right == it },
                ).all { it }
            }.map { PlacedTile(it, centerSpace) }

            if (satisfiedRotations.isNotEmpty()) {
                put(centerSpace, satisfiedRotations)
            }
        }
    }
}
