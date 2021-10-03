package pl.gieted.flappy_bird.game

actual class HighScoreRepository {
    actual suspend fun loadHighScore(): Int = 0

    actual suspend fun saveHighScore(score: Int) {
    }
}
