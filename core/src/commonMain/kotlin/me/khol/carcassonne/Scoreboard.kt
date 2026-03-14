package me.khol.carcassonne

@ConsistentCopyVisibility
data class Scoreboard private constructor(
    private val scores: Map<Player, Int>,
) {

    constructor(players: Collection<Player>) : this(
        scores = players.associateWith { 0 },
    )

    fun addScore(player: Player, points: Int): Scoreboard = copy(
        scores = scores + (player to (scores.getValue(player)) + points),
    )

    fun addScores(players: Collection<Player>, points: Int): Scoreboard =
        players.fold(this) { scoreboard, player ->
            scoreboard.addScore(player = player, points = points)
        }

    fun getScore(player: Player): Int = scores.getValue(player)
}
