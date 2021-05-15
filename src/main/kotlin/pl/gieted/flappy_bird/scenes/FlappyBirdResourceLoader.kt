package pl.gieted.flappy_bird.scenes

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import pl.gieted.flappy_bird.Renderer
import pl.gieted.flappy_bird.engine.ResourceLoader
import pl.gieted.flappy_bird.engine.scale
import pl.gieted.flappy_bird.scenes.objects.BirdColor
import processing.core.PImage

class FlappyBirdResourceLoader(renderer: Renderer) : ResourceLoader(renderer) {
    suspend fun loadResources(): FlappyBirdResources = coroutineScope {
        val backgroundDay = async { loadImage("images/background-day.png") }
        val backgroundNight = async { loadImage("images/background-night.png") }
        val base = async { loadImage("images/base.png") }
        val gameOver = async { loadImage("images/gameover.png") }
        val message = async { loadImage("images/message.png") }
        val greenPipe = async { loadImage("images/pipe-green.png") }
        val redPipe = async { loadImage("images/pipe-red.png") }
        val birds = async {
            BirdColor.values().associate { color ->
                val colorName = color.name.replaceFirstChar { it.lowercase() }
                val downFlap =
                    async { loadImage("images/${colorName}bird-downflap.png").also { resizeBird(it) } }

                val midFlap =
                    async { loadImage("images/${colorName}bird-midflap.png").also { resizeBird(it) } }

                val upFlap =
                    async { loadImage("images/${colorName}bird-upflap.png").also { resizeBird(it) } }
                color to FlappyBirdResources.Bird(downFlap.await(), midFlap.await(), upFlap.await())
            }
        }
        val numbers = async { (0..9).map { loadImage("images/$it.png") } }
        
        val sounds = async {
            val hit = loadSound("sounds/hit.wav")
            val wing = loadSound("sounds/wing.wav")
            val point = loadSound("sounds/point.wav")
            FlappyBirdResources.Sounds(hit, wing, point)
        }

        FlappyBirdResources(
            backgroundDay.await().also { resizeBackground(it) },
            backgroundNight.await().also { resizeBackground(it) },
            base.await().also { it.scale(1.5) },
            gameOver.await(),
            message.await().also { it.scale(1.5) },
            greenPipe.await().also { resizePipe(it) },
            redPipe.await().also { resizePipe(it) },
            birds.await(),
            numbers.await(),
            sounds.await()
        )
    }

    private fun resizePipe(texture: PImage) {
        texture.scale(2.0)
    }

    private fun resizeBackground(texture: PImage) {
        texture.resize(
            texture.width * (texture.height.toDouble() / Renderer.windowHeight).toInt(),
            Renderer.windowHeight
        )
    }

    private fun resizeBird(texture: PImage) {
        texture.resize(50, 35)
    }
}
