package pl.gieted.flappy_bird.game

import pl.gieted.flappy_bird.Renderer
import pl.gieted.flappy_bird.engine.*
import pl.gieted.flappy_bird.game.objects.*

class GameScene(renderer: Renderer, private val resources: Resources) : Scene(renderer) {

    companion object {
        const val firstPipeOffset = 1000
        const val pipeOffset = 350
        const val groundLevel = -290
    }

    private val startScreen = StartScreen(renderer, resources.images.message)
    private var score = 0
    private val bird = Bird(renderer, getBirdTexture(), this::onDeath, resources.sounds.wing)
    private var gameStarted = false

    private fun getBackgroundTexture() = if ((1..4).random() < 4) {
        resources.images.backgroundDay
    } else resources.images.backgroundNight

    private fun getBirdTexture() = when ((1..3).random()) {
        1 -> resources.images.birds[Bird.Color.Red]!!
        2 -> resources.images.birds[Bird.Color.Blue]!!
        else -> resources.images.birds[Bird.Color.Yellow]!!
    }

    private fun onDeath() {
        addObject(DeathFlash(renderer))
        resources.sounds.hit.play()
    }

    private fun getPipeTexture() =
        if ((1..100).random() == 100) resources.images.redPipe else resources.images.greenPipe


    private fun startGame() {
        gameStarted = true
        startScreen.fadeOut()
    }

    private fun incrementScore() {
        resources.sounds.point.play()
        score++
    }

    override fun setup() {
        super.setup()
        addObject(DipFromBlack(renderer))
        val backgroundTexture = getBackgroundTexture()
        addObject(
            ScrollingElement(
                renderer,
                { Sprite(renderer, texture = backgroundTexture) },
                parallax = -1.8,
                offset = -11.0
            )
        )
        addObject(
            ScrollingElement(
                renderer,
                { Sprite(renderer, Vector2(0, -Vector2.halfScreen.y), texture = resources.images.base) },
                zIndex = 5
            )
        )
        addObject(startScreen)
        addObject(bird)
    }

    override fun draw() {
        with(renderer) {
            if (mousePressedThisFrame && bird.isAlive && bird.bounds.top < camera.bounds.top) {
                once("startGame") {
                    startGame()
                }
                bird.swing()
            }

            if (bird.position.y < groundLevel) {
                bird.position = Vector2(bird.position.x, groundLevel)
                if (bird.isAlive) {
                    bird.kill()
                }
            }

            camera.position = Vector2(bird.position.x - Bird.xOffset, 0)
        }
        super.draw()
    }
}
