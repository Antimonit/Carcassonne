package me.khol.carcassonne

open class ElementKey<E : Element<ElementPosition>> {

    data object Field : ElementKey<Element.Field>()
    data object Road : ElementKey<Element.Road>()
    data object City : ElementKey<Element.City>()
    data object Monastery : ElementKey<Element.Monastery>()
    data object Garden : ElementKey<Element.Garden>()
    data object RiverStart : ElementKey<Element.RiverStart>()
    data object River : ElementKey<Element.River>()
    data object RiverEnd : ElementKey<Element.RiverEnd>()
    data object CropCircle : ElementKey<Element.CropCircle>()
}
