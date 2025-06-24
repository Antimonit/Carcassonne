package me.khol.carcassonne

sealed class ElementKey<P : ElementPosition, G : ElementGroup<P>> {

    data object Field : ElementKey<ElementPosition.SplitEdge, ElementGroup.Field>()
    data object Road : ElementKey<ElementPosition.Edge, ElementGroup.Road>()
    data object City : ElementKey<ElementPosition.Edge, ElementGroup.City>()
    data object Monastery : ElementKey<ElementPosition.Center, ElementGroup.Monastery>()
    data object Garden : ElementKey<ElementPosition.Center, ElementGroup.Garden>()
    data object RiverStart : ElementKey<ElementPosition.Center, ElementGroup.RiverStart>()
    data object River : ElementKey<ElementPosition.Edge, ElementGroup.River>()
    data object RiverEnd : ElementKey<ElementPosition.Center, ElementGroup.RiverEnd>()
    data object CropCircle : ElementKey<ElementPosition.Center, ElementGroup.CropCircle>()
}
