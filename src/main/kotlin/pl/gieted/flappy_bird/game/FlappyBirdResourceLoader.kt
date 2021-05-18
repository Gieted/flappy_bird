package pl.gieted.flappy_bird.game

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.engine.ResourceLoader
import pl.gieted.flappy_bird.engine.scale
import pl.gieted.flappy_bird.game.objects.Bird
import processing.core.PImage

class FlappyBirdResourceLoader(renderer: Renderer) : ResourceLoader(renderer) {
    private companion object {
        const val pipeScale = 2.0
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
                Bird.Color.values().associate { color ->
                    val colorName = color.name.replaceFirstChar { it.lowercase() }
                    val downFlap =
                        loadImage("${colorName}bird-downflap.png").also { resizeBird(it) }

                    val midFlap =
                        loadImage("${colorName}bird-midflap.png").also { resizeBird(it) }

                    val upFlap =
                        loadImage("${colorName}bird-upflap.png").also { resizeBird(it) }
                    color to Resources.Images.Bird(downFlap, midFlap, upFlap)
                }

            val numbers = (0..9).map { loadImage("$it.png") }
            Resources.Images(
                backgroundDay.also { resizeBackground(it) },
                backgroundNight.also { resizeBackground(it) },
                base.also { it.scale(2.0) },
                gameOver,
                message.also { it.scale(1.5) },
                greenPipe.also { it.scale(pipeScale) },
                redPipe.also { it.scale(pipeScale) },
                birds,
                numbers
            )
        }

        val sounds = async(Dispatchers.IO) {
            val hit = loadSound("hit.wav")
            val wing = loadSound("wing.wav")
            val point = loadSound("point.wav")
            Resources.Sounds(hit, wing, point)
        }

        Resources(
            images.await(),
            sounds.await()
        )
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
