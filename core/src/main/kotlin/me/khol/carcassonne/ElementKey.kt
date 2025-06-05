package me.khol.carcassonne

sealed class ElementKey<P : Position, G : ElementGroup<P>> {

    data object Field : ElementKey<Position.SplitEdge, ElementGroup.Field>()
    data object Road : ElementKey<Position.Edge, ElementGroup.Road>()
    data object City : ElementKey<Position.Edge, ElementGroup.City>()
    data object Monastery : ElementKey<Position.Center, ElementGroup.Center>()
    data object Garden : ElementKey<Position.Center, ElementGroup.Center>()
    data object RiverStart : ElementKey<Position.Center, ElementGroup.Center>()
    data object River : ElementKey<Position.Edge, ElementGroup.Edge>()
    data object RiverEnd : ElementKey<Position.Center, ElementGroup.Center>()
    data object CropCircle : ElementKey<Position.Center, ElementGroup.Center>()
}
