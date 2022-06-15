package me.khol.carcassonne

sealed interface Boon {

    enum class City : Boon {
        CoatOfArms,
        Cathedral,
    }

    enum class Road : Boon {
        Inn,
    }
}
