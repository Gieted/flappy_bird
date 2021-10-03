package pl.gieted.flappy_bird.game

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import pl.gieted.flappy_bird.engine.Image
import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.engine.ResourceLoader
import pl.gieted.flappy_bird.engine.scale
import pl.gieted.flappy_bird.game.objects.Bird

class FlappyBirdResourceLoader(renderer: Renderer) : ResourceLoader(renderer) {
    private companion object {
        const val pipeScale = 1.9
    }

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
                        loadImage("${colorName}bird-downflap.png").also { resizeBird(it) }

                    val midFlap =
                        loadImage("${colorName}bird-midflap.png").also { resizeBird(it) }

                    val upFlap =
                        loadImage("${colorName}bird-upflap.png").also { resizeBird(it) }
                    Resources.Images.Bird(downFlap, midFlap, upFlap)
                }

            val digits = (0..9).map { loadImage("$it.png") }
            Resources.Images(
                backgroundDay.also { resizeBackground(it) },
                backgroundNight.also { resizeBackground(it) },
                base.also { it.scale(2.0) },
                gameOver.also { it.scale(1.5) },
                message.also { it.scale(1.5) },
                greenPipe.also { it.scale(pipeScale) },
                redPipe.also { it.scale(pipeScale) },
                birds,
                digits.onEach { it.scale(1.25) }
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

    private fun resizeBackground(texture: Image) {
        texture.resize(
            texture.width * (texture.height.toDouble() / Renderer.windowHeight).toInt(),
            Renderer.windowHeight
        )
    }

    private fun resizeBird(texture: Image) {
        texture.resize(50, 35)
    }
}
