package me.khol.carcassonne

sealed class Element<P : Position, G : ElementGroup<P>> {

    data object Field : Element<Position.SplitEdge, ElementGroup.Field>()
    data object Road : Element<Position.Edge, ElementGroup.Road>()
    data object City : Element<Position.Edge, ElementGroup.City>()
    data object Monastery : Element<Position.Center, ElementGroup.Center>()
    data object Garden : Element<Position.Center, ElementGroup.Center>()
    data object RiverStart : Element<Position.Center, ElementGroup.Center>()
    data object River : Element<Position.Edge, ElementGroup.Edge>()
    data object RiverEnd : Element<Position.Center, ElementGroup.Center>()
    data object CropCircle : Element<Position.Center, ElementGroup.Center>()
}
