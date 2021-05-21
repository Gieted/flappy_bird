package pl.gieted.flappy_bird.game

import kotlinx.coroutines.*
import java.io.File
import java.util.*

class HighScoreRepository {

    companion object {
        const val filePath = "./high_score.data"
    }

    suspend fun loadHighScore(): Int = coroutineScope {
        val file = File(filePath)

        if (!file.exists()) {
            saveHighScore(0)
            return@coroutineScope 0
        }

        try {
            val fileText = async(Dispatchers.IO) { file.readText() }
            Base64.getDecoder().decode(fileText.await()).let { String(it) }.toInt()
        } catch (exception: Exception) {
            saveHighScore(0)
            0
        }
    }

    suspend fun saveHighScore(score: Int) = coroutineScope {
        val encodedString = Base64.getEncoder().encodeToString(score.toString().toByteArray())
        launch(Dispatchers.IO) {
            try {
                File(filePath).writeText(encodedString)
                println("Saved high score")
            } catch (exception: Exception) {
                println("There was an error while trying to save high score")
            }
        }
    }
}
