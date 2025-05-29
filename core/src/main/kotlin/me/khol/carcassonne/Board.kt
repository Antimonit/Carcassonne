package me.khol.carcassonne

@ConsistentCopyVisibility
data class Board private constructor(
    private val tiles: Map<Coordinates, RotatedTile>,
    private val openSpaces: Set<Coordinates>,
) {

    companion object {
        val empty = Board(
            tiles = emptyMap(),
            openSpaces = setOf(Coordinates(0, 0)),
        )

        fun starting(
            startingTile: Tile,
        ) = empty.placeTile(
            coordinates = Coordinates(0, 0),
            tile = RotatedTile(startingTile, Rotation.ROTATE_0),
        )
    }

    fun getTile(coordinates: Coordinates): RotatedTile? = tiles[coordinates]

    fun placeTile(coordinates: Coordinates, tile: RotatedTile): Board {
        require(coordinates in openSpaces) {
            "Cannot place tile ${tile.tile.name} at $coordinates as it is not connected to the rest of the board."
        }

        return copy(
            tiles = tiles + (coordinates to tile),
            openSpaces = openSpaces
                - coordinates
                + coordinates.neighbors.filterNot { side ->
                    tiles.containsKey(side)
                },
        )
    }

    fun possibleSpacesForTile(tile: Tile): Map<Coordinates, List<PlacedTile>> = buildMap {
        val rotatedTiles: List<RotatedTile> = Rotation.entries.map { rotation ->
            RotatedTile(tile, rotation)
        }
        openSpaces.forEach { centerSpace ->
            val top = getTile(centerSpace.top)?.rotatedEdges?.bottom
            val right = getTile(centerSpace.right)?.rotatedEdges?.left
            val bottom = getTile(centerSpace.bottom)?.rotatedEdges?.top
            val left = getTile(centerSpace.left)?.rotatedEdges?.right

            val satisfiedRotations = rotatedTiles.filter { rotatedTile ->
                listOfNotNull(
                    top?.let { rotatedTile.rotatedEdges.top == it },
                    bottom?.let { rotatedTile.rotatedEdges.bottom == it },
                    left?.let { rotatedTile.rotatedEdges.left == it },
                    right?.let { rotatedTile.rotatedEdges.right == it },
                ).all { it }
            }.map { PlacedTile(it, centerSpace) }

            if (satisfiedRotations.isNotEmpty()) {
                put(centerSpace, satisfiedRotations)
            }
        }
    }
}
