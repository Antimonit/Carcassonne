package me.khol.carcassonne

import me.khol.carcassonne.feature.City
import me.khol.carcassonne.feature.Feature
import me.khol.carcassonne.feature.Field
import me.khol.carcassonne.feature.Garden
import me.khol.carcassonne.feature.Monastery
import me.khol.carcassonne.feature.PlacedElement
import me.khol.carcassonne.feature.Road
import me.khol.carcassonne.feature.getCityFeatures
import me.khol.carcassonne.feature.getFieldFeatures
import me.khol.carcassonne.feature.getGardenFeatures
import me.khol.carcassonne.feature.getMonasteryFeatures
import me.khol.carcassonne.feature.getRoadFeatures

data class Board(
    val tiles: Map<Coordinates, RotatedTile>,
    val figures: Map<Coordinates, List<PlacedFigure>>,
) {

    val openSpaces: Set<Coordinates> = tiles.keys
        .asSequence()
        .flatMap { it.neighbors }
        .plus(Coordinates(0, 0))
        .distinct()
        .minus(tiles.keys)
        .toSet()

    val cityFeatures: Set<City> by lazy { getCityFeatures() }
    val roadFeatures: Set<Road> by lazy { getRoadFeatures() }
    val fieldFeatures: Set<Field> by lazy { getFieldFeatures() }
    val monasteryFeatures: Set<Monastery> by lazy { getMonasteryFeatures() }
    val gardenFeatures: Set<Garden> by lazy { getGardenFeatures() }

    val allFeatures: Set<Feature> by lazy {
        // Ordering matters.
        // See https://wikicarpedia.com/car/Scoring_During_the_Game
        roadFeatures + cityFeatures + monasteryFeatures + gardenFeatures + fieldFeatures
    }

    companion object {
        val empty = Board(
            tiles = emptyMap(),
            figures = emptyMap(),
        )

        fun starting(
            startingTile: Tile,
        ) = empty.placeTile(
            placedTile = startingTile.rotated(Rotation.ROTATE_0).placed(0, 0),
            placedFigures = emptyList(),
        )
    }

    fun getTile(coordinates: Coordinates): RotatedTile? = tiles[coordinates]

    fun getFigures(coordinates: Coordinates): List<PlacedFigure> = figures[coordinates].orEmpty()

    fun getFigures(placedElement: PlacedElement<*>): List<PlacedFigure> = figures[placedElement.coordinates].orEmpty().filter { it.placedElement == placedElement }

    fun placeTile(placedTile: PlacedTile, placedFigures: List<PlacedFigure>): Board {
        val coordinates = placedTile.coordinates
        val rotatedTile = placedTile.rotatedTile
        val tile = rotatedTile.tile

        require(coordinates !in tiles.keys) {
            "Cannot place tile ${tile.name} at $coordinates as another tile is already placed there."
        }
        require(coordinates in openSpaces) {
            "Cannot place tile ${tile.name} at $coordinates as it is not connected to the rest of the board."
        }
        require(rotatedTile.placed(coordinates) in possibleSpacesForTile(tile).getOrElse(coordinates) { emptyList() }) {
            "Cannot place tile ${tile.name} at $coordinates as it does not match edges with one or more neighbors."
        }

        val boardWithTile = copy(
            tiles = tiles + (coordinates to rotatedTile),
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

    fun removeFigures(figures: Set<PlacedFigure>): Board = copy(
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
        // TODO: Improve the error message
        "Cannot add a figure to an already occupied feature"
    }
}

fun Board.elementToFeature(placedElement: PlacedElement<*>): Feature =
    requireNotNull(allFeatures.find { it.placedElements.contains(placedElement) }) {
        "Could not find element: \n$placedElement\nin any of the board features:\n${allFeatures.joinToString(separator = "\n")}"
    }
