package pl.gieted.flappy_bird.game

import kotlinx.coroutines.*
import java.io.File

actual class HighScoreRepository(private val filesDir: File) {

    companion object {
        const val filePath = "./high_score.data"
    }

    actual suspend fun loadHighScore(): Int = coroutineScope {
        val file = File(filesDir, filePath)

        if (!file.exists()) {
            saveHighScore(0)
            return@coroutineScope 0
        }

        try {
            val fileText = async(Dispatchers.IO) { file.readText() }
            fileText.await().toInt()
        } catch (exception: Exception) {
            saveHighScore(0)
            0
        }
    }

    actual suspend fun saveHighScore(score: Int): Unit = coroutineScope {
        launch(Dispatchers.IO) {
            try {
                File(filesDir, filePath).writeText(score.toString())
                println("Saved high score")
            } catch (exception: Exception) {
                println("There was an error while trying to save high score")
                exception.printStackTrace()
            }
        }
    }
}
