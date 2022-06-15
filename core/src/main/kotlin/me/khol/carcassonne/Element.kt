package me.khol.carcassonne

sealed class Element<P : Position, Set : Positions<P>> {

    object Field : Element<Position.SplitEdge, Positions.Field>()
    object Road : Element<Position.Edge, Positions.Road>()
    object City : Element<Position.Edge, Positions.City>()
    object Monastery : Element<Position.Center, Positions.Center>()
    object Garden : Element<Position.Center, Positions.Center>()
    object RiverStart : Element<Position.Center, Positions.Center>()
    object River : Element<Position.Edge, Positions.Edge>()
    object RiverEnd : Element<Position.Center, Positions.Center>()
    object CropCircle : Element<Position.Center, Positions.Center>()

    override fun toString(): String = javaClass.simpleName
}
