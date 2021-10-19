package pl.gieted.flappy_bird.game

expect class HighScoreRepository {
    suspend fun loadHighScore(): Int

    suspend fun saveHighScore(score: Int)
}
