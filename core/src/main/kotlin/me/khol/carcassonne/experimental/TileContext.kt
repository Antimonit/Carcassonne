package me.khol.carcassonne.experimental

interface TileContext {

    operator fun <E : Element> get(key: Key<E>): E?

    operator fun plus(context: TileContext): TileContext

    interface Key<E : Element>

    interface Element : TileContext {
        val key: Key<*>

        public override operator fun <E : Element> get(key: Key<E>): E? =
            @Suppress("UNCHECKED_CAST")
            if (this.key == key) this as E else null
    }
}

private fun <F, E : Collection<F>> Collection<E>.containsNone(elements: Collection<@UnsafeVariance F>): Boolean {
    return none { collection: Collection<F> ->
        elements.map { element -> collection.contains(element) }.reduce(Boolean::or)
    }
}