package me.khol.carcassonne

import me.khol.carcassonne.feature.Feature
import me.khol.carcassonne.feature.PlacedElement
import me.khol.carcassonne.feature.getAllFeatures

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
            tile = startingTile.rotated(Rotation.ROTATE_0),
            placedFigures = emptyList(),
        )
    }

    fun getTile(coordinates: Coordinates): RotatedTile? = tiles[coordinates]

    fun getFigures(coordinates: Coordinates): List<PlacedFigure> = figures[coordinates].orEmpty()

    fun getFigures(placedElement: PlacedElement<*>): List<PlacedFigure> = figures[placedElement.coordinates].orEmpty().filter { it.placedElement == placedElement }

    fun placeTile(coordinates: Coordinates, tile: RotatedTile, placedFigures: List<PlacedFigure>): Board {
        require(coordinates !in tiles.keys) {
            "Cannot place tile ${tile.tile.name} at $coordinates as another tile is already placed there."
        }
        require(coordinates in openSpaces) {
            "Cannot place tile ${tile.tile.name} at $coordinates as it is not connected to the rest of the board."
        }
        require(tile.placed(coordinates) in possibleSpacesForTile(tile.tile).getValue(coordinates)) {
            "Cannot place tile ${tile.tile.name} at $coordinates as it does not match edges with one or more neighbors."
        }

        val boardWithTile = copy(
            tiles = tiles + (coordinates to tile),
        )

        placedFigures.forEach {
            boardWithTile.checkOccupiedFeatures(it)
        }

        return boardWithTile.copy(
            figures = figures + mapOf(coordinates to placedFigures),
        )
    }

    fun possibleSpacesForTile(tile: Tile): Map<Coordinates, List<PlacedTile>> =
        openSpaces
            .associateWith { centerSpace -> validTileRotations(centerSpace, tile) }
            .filterValues { it.isNotEmpty() }

    fun removeFigures(figures: List<PlacedFigure>): Board = copy(
        figures = this.figures.mapValues { (_, placedFigures) ->
            placedFigures - figures
        }
    )
}

fun Board.validTileRotations(
    centerSpace: Coordinates,
    tile: Tile,
): List<PlacedTile> {
    fun RotatedTile.rotatedEdges(): Tile.Edges = this.tile.edges.rotate(rotation)

    val top = getTile(centerSpace.top)?.rotatedEdges()?.bottom
    val right = getTile(centerSpace.right)?.rotatedEdges()?.left
    val bottom = getTile(centerSpace.bottom)?.rotatedEdges()?.top
    val left = getTile(centerSpace.left)?.rotatedEdges()?.right

    val rotatedTiles = Rotation.entries.map { rotation ->
        tile.rotated(rotation)
    }
    return rotatedTiles.filter { rotatedTile ->
        val rotatedEdges = rotatedTile.rotatedEdges()
        listOfNotNull(
            top?.let { rotatedEdges.top == it },
            bottom?.let { rotatedEdges.bottom == it },
            left?.let { rotatedEdges.left == it },
            right?.let { rotatedEdges.right == it },
        ).all { it }
    }.map { it.placed(centerSpace) }
}

fun Board.checkOccupiedFeatures(placedFigure: PlacedFigure) {
    val feature = elementToFeature(placedFigure.placedElement)
    val figure = placedFigure.figure.figure
    val player = placedFigure.figure.player
    require(figure.canBePlaced(feature, player)) {
        "Cannot add a figure to an already occupied feature"
    }
}

fun Board.elementToFeature(placedElement: PlacedElement<*>): Feature {
    return getAllFeatures().first { it.placedElements.contains(placedElement) }
}
