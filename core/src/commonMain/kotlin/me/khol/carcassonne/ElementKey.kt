package me.khol.carcassonne

sealed class ElementKey<P : ElementPosition, E : Element<P>> {

    data object Field : ElementKey<ElementPosition.SplitEdge, Element.Field>()
    data object Road : ElementKey<ElementPosition.Edge, Element.Road>()
    data object City : ElementKey<ElementPosition.Edge, Element.City>()
    data object Monastery : ElementKey<ElementPosition.Center, Element.Monastery>()
    data object Garden : ElementKey<ElementPosition.Center, Element.Garden>()
    data object RiverStart : ElementKey<ElementPosition.Center, Element.RiverStart>()
    data object River : ElementKey<ElementPosition.Edge, Element.River>()
    data object RiverEnd : ElementKey<ElementPosition.Center, Element.RiverEnd>()
    data object CropCircle : ElementKey<ElementPosition.Center, Element.CropCircle>()
}
