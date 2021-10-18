package pl.gieted.flappy_bird.game

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.engine.ResourceLoader
import pl.gieted.flappy_bird.game.objects.Bird

class FlappyBirdResourceLoader(renderer: Renderer) : ResourceLoader(renderer) {
    
    suspend fun loadResources(): Resources = coroutineScope {
        val images = async {
            val backgroundDay = loadImage("background-day.png")
            val backgroundNight = loadImage("background-night.png")
            val base = loadImage("base.png")
            val gameOver = loadImage("gameover.png")
            val message = loadImage("message.png")
            val greenPipe = loadImage("pipe-green.png")
            val redPipe = loadImage("pipe-red.png")
            val birds =
                Bird.Color.values().associateWith { color ->
                    val colorName = color.name.replaceFirstChar { it.lowercase() }
                    val downFlap =
                        loadImage("${colorName}bird-downflap.png")

                    val midFlap =
                        loadImage("${colorName}bird-midflap.png")

                    val upFlap =
                        loadImage("${colorName}bird-upflap.png")
                    Resources.Images.Bird(downFlap, midFlap, upFlap)
                }

            val digits = (0..9).map { loadImage("$it.png") }
            Resources.Images(
                backgroundDay,
                backgroundNight,
                base,
                gameOver,
                message,
                greenPipe,
                redPipe,
                birds,
                digits
            )
        }

        val sounds = async {
            val hit = loadSound("hit.wav").also { it.amp(Renderer.soundVolume.toFloat() / 2) }
            val wing = loadSound("wing.wav")
            val point = loadSound("point.wav")
            val swoosh = loadSound("swoosh.wav")
            val die = loadSound("die.wav").also { it.amp(Renderer.soundVolume.toFloat() / 4) }
            Resources.Sounds(hit, wing, point, swoosh, die)
        }

        Resources(
            images.await(),
            sounds.await()
        )
    }
}
