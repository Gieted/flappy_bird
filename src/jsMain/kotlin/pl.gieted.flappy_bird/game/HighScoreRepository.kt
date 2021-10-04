package pl.gieted.flappy_bird.game

import kotlinx.browser.document

actual class HighScoreRepository {
    private fun encode(score: Int) = score * 127 + 52

    private fun decode(score: Int) = if ((score - 52) % 127 == 0) score / 127 else 0 

    actual suspend fun loadHighScore(): Int = run{
        try {
            document.cookie.split("=")[1].toInt()
        } catch (exception: Exception) {
            0
        }
    }.let { decode(it) }

    actual suspend fun saveHighScore(score: Int) {
        val encodedScore = encode(score)
        document.cookie = "high_score=${encodedScore}; path=/; expires=Fri, 31 Dec 9999 23:59:59 GMT"
    }
}
