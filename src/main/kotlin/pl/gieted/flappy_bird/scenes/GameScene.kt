package pl.gieted.flappy_bird.scenes

import pl.gieted.flappy_bird.Renderer
import pl.gieted.flappy_bird.engine.*
import pl.gieted.flappy_bird.scenes.objects.*
import processing.core.PImage

class GameScene(renderer: Renderer, private val resources: FlappyBirdResources) : Scene(renderer) {
    private val curtain = Curtain(renderer)
    private val startScreen = Sprite(renderer, Vector2(0, -75), texture = resources.message)
    private val bird = Bird(renderer, getBirdTextures(), this::onDeath, resources.sounds.wing)
    private var gameStarted = false
    private var gameOver = false
    private val background = ScrollingBackground(renderer, getBackgroundTexture(), parallax = -3.0)
    private val pipes = mutableListOf<Pipe>()
    private var score = 0
    private var startPosition: Double? = null

    companion object {
        const val firstPipeOffset = 1000
        const val groundLevel = 315
        const val pipeOffset = 350
    }

    private fun onDeath() {
        gameOver = true
        addObject(DeathFlash(renderer))
        background.paused = true
        resources.sounds.hit.play()
    }

    private val pipeTexture = if ((1..100).random() == 100) resources.redPipe else resources.greenPipe

    private fun spawnPipe() {
        val xPos = if (pipes.isEmpty()) bird.position.x + firstPipeOffset else pipes.last().position.x + pipeOffset
        val pipe = Pipe(renderer, Vector2(xPos, 0), pipeTexture)
        pipes.add(pipe)
        addObject(pipe)
    }

    private fun getBackgroundTexture(): PImage {
        return if ((1..4).random() < 4) {
            resources.backgroundDay
        } else resources.backgroundNight
    }

    private fun getBirdTextures(): FlappyBirdResources.Bird {
        return when ((1..3).random()) {
            1 -> resources.birds[BirdColor.Red]!!
            2 -> resources.birds[BirdColor.Blue]!!
            else -> resources.birds[BirdColor.Yellow]!!
        }
    }

    override fun setup() {
        super.setup()
        addObject(curtain)
        addObject(background)
        addObject(ScrollingBackground(renderer, resources.base, false, zIndex = 5))
        addObject(startScreen)
        addObject(bird)
    }

    private fun startGame() {
        gameStarted = true
        bird.awake()
        for (i in 1..10) {
            spawnPipe()
        }
        startPosition = bird.position.x
    }

    private fun onScore() {
        resources.sounds.point.play()
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            curtain.fadeIn(0.002, deltaTime)
            if (!gameStarted) {
                if (mousePressed) {
                    startGame()
                }
            }

            if (gameStarted) {
                if (startScreen.opacity > 0.0) {
                    startScreen.opacity -= 0.005 * deltaTime
                }

            }

            startScreen.position = Vector2(bird.position.x + Bird.xOffset, startScreen.position.y)

            if (gameStarted) {
                val distance = (bird.position.x - startPosition!!).toInt() - firstPipeOffset + pipeOffset + 50
                val nextScore = if (distance > 0) distance / pipeOffset else 0
                if (nextScore > score) {
                    onScore()
                }
                score = nextScore
            }
        }
    }
}
