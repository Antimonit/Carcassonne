package me.khol.carcassonne

sealed class Element<P : Position, G : ElementGroup<P>> {

    object Field : Element<Position.SplitEdge, ElementGroup.Field>()
    object Road : Element<Position.Edge, ElementGroup.Road>()
    object City : Element<Position.Edge, ElementGroup.City>()
    object Monastery : Element<Position.Center, ElementGroup.Center>()
    object Garden : Element<Position.Center, ElementGroup.Center>()
    object RiverStart : Element<Position.Center, ElementGroup.Center>()
    object River : Element<Position.Edge, ElementGroup.Edge>()
    object RiverEnd : Element<Position.Center, ElementGroup.Center>()
    object CropCircle : Element<Position.Center, ElementGroup.Center>()

    override fun toString(): String = javaClass.simpleName
}
