package me.khol.carcassonne

sealed class Element<P : Position> {

    object Field : Element<Position.SplitEdge>()
    object Road : Element<Position.Edge>()
    object City : Element<Position.Edge>()
    object Monastery : Element<Position.Center>()
    object Garden : Element<Position.Center>()
    object RiverStart : Element<Position.Center>()
    object River : Element<Position.Edge>()
    object RiverEnd : Element<Position.Center>()
    object CropCircle : Element<Position.Center>()

    override fun toString(): String = javaClass.simpleName
}
