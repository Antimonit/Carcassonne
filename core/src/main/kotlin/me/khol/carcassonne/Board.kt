package me.khol.carcassonne

import me.khol.carcassonne.tiles.basic.D

class Board(
    startingTile: Tile,
) {

    private val tiles: MutableMap<Coordinates, RotatedTile> = mutableMapOf()
    private val openSpaces: MutableSet<Coordinates> = mutableSetOf(Coordinates(0, 0))

    init {
        set(Coordinates(0, 0), RotatedTile(startingTile, Rotation.ROTATE_0))
    }

    fun get(coordinates: Coordinates): RotatedTile? = tiles[coordinates]

    fun set(coordinates: Coordinates, tile: RotatedTile) {
        require(coordinates in openSpaces)
        tiles[coordinates] = tile
        openSpaces -= coordinates
        coordinates.neighbors.forEach { side ->
            if (!tiles.containsKey(side)) {
                openSpaces.add(side)
            }
        }
    }

    fun possibleSpacesForTile(tile: Tile): Map<Coordinates, List<PlacedTile>> = buildMap {
        val rotatedTiles: List<RotatedTile> = Rotation.values().map { rotation ->
            RotatedTile(tile, rotation)
        }
        openSpaces.forEach { centerSpace ->
            val top = this@Board.get(centerSpace.top)?.rotatedEdges?.bottom
            val right = this@Board.get(centerSpace.right)?.rotatedEdges?.left
            val bottom = this@Board.get(centerSpace.bottom)?.rotatedEdges?.top
            val left = this@Board.get(centerSpace.left)?.rotatedEdges?.right

            val satisfiedRotations = rotatedTiles.filter { rotatedTile ->
                listOfNotNull(
                    top?.let { rotatedTile.rotatedEdges.top == it },
                    bottom?.let { rotatedTile.rotatedEdges.bottom == it },
                    left?.let { rotatedTile.rotatedEdges.left == it },
                    right?.let { rotatedTile.rotatedEdges.right == it },
                ).all { it }
            }.map { PlacedTile(centerSpace, it) }

            if (satisfiedRotations.isNotEmpty()) {
                put(centerSpace, satisfiedRotations)
            }
        }
    }
}
