package pl.gieted.flappy_bird.game

import kotlinx.coroutines.launch
import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.engine.*
import pl.gieted.flappy_bird.game.objects.*
import kotlin.random.Random

class GameScene(renderer: Renderer, private val resources: Resources, private var highScore: Int) : Scene(renderer) {

    companion object {
        const val firstPipeOffset = 1000
        const val pipeOffset = 350.0
        const val groundLevel = -29
        const val maxPipeHeight = 225.0
        const val minPipeHeight = -125.0
        const val scoringOffset = 25 
    }

    private val startScreen = StartScreen(renderer, resources.images.message)
    private val scoreDisplay = ScoreDisplay(renderer, resources.images.digits).also { it.score = highScore }

    private var score: Int = 0
        set(value) {
            if (value > field) {
                resources.sounds.point.play()
                scoreDisplay.score = value
            }
            field = limit(value, lowerBound = 0)
        }

    private val bird = Bird(renderer, getBirdTexture(), this::onDeath, resources.sounds.wing, resources.sounds.die)
    private val dipToBlack = DipToBlack(renderer)

    private fun getBackgroundTexture() = if ((1..4).random() < 4) {
        resources.images.backgroundDay
    } else resources.images.backgroundNight

    private fun getBirdTexture() = when ((1..3).random()) {
        1 -> resources.images.birds[Bird.Color.Red]!!
        2 -> resources.images.birds[Bird.Color.Blue]!!
        else -> resources.images.birds[Bird.Color.Yellow]!!
    }

    private var deathLockTime = 0.0
        set(value) {
            field = limit(value, lowerBound = 0.0)
        }

    private fun onDeath() {
        addObject(DeathFlash(renderer))
        resources.sounds.hit.play()
        addObject(GameOver(renderer, resources.images.gameOver))
        deathLockTime = 500.0

        if (score > highScore) {
            highScore = score
            lifecycleScope.launch {
                HighScoreRepository().saveHighScore(highScore)
            }
        }
    }

    private var pipesCount = 0

    private fun getPipeTexture() = if (++pipesCount < 100) resources.images.greenPipe else resources.images.redPipe


    private fun getNextPipeHeight(): Double = Random.nextDouble(minPipeHeight, maxPipeHeight)

    private fun startGame() {
        startScreen.fadeOut()

        addObject(
            CollidableScrollingElement(
                renderer,
                { Pipe(renderer, Vector2(0, getNextPipeHeight()), texture = getPipeTexture()) },
                offset = pipeOffset,
                startXPos = bird.position.x + firstPipeOffset
            )
        )

        scoreDisplay.score = score
    }

    override fun setup() {
        super.setup()
        addObject(DipFromBlack(renderer))
        resources.sounds.swoosh.play()
        val backgroundTexture = getBackgroundTexture()
        addObject(
            ScrollingElement(
                renderer,
                { Sprite(renderer, texture = backgroundTexture) },
                parallax = -0.8,
                offset = backgroundTexture.width - 11.0
            )
        )
        addObject(
            ScrollingElement(
                renderer,
                { Sprite(renderer, Vector2(0, -Vector2.halfScreen.y), texture = resources.images.base) },
                zIndex = 5,
                offset = resources.images.base.width - 1.0
            )
        )
        addObject(startScreen)
        addObject(bird)
        addObject(scoreDisplay)
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            val distanceFlownFromFirstPipe: Double = limit(bird.distanceFlown - firstPipeOffset + scoringOffset, lowerBound = 0.0)
            score = ((distanceFlownFromFirstPipe) / pipeOffset).toInt()
                .let { if (distanceFlownFromFirstPipe > 0.0) it + 1 else it }

            if (bird.position.y < groundLevel) {
                bird.position = Vector2(bird.position.x, groundLevel)
                if (bird.isAlive) {
                    bird.kill()
                }
            }

            if (mousePressedThisFrame && bird.isAlive && bird.bounds.top < camera.bounds.top) {
                once("startGame") {
                    startGame()
                }
                bird.swing()
            }

            camera.position = Vector2(bird.position.x - Bird.xOffset, 0)

            if (!bird.isAlive && mousePressedThisFrame && deathLockTime == 0.0) {
                once("dipToBlack") {
                    addObject(dipToBlack)
                    resources.sounds.swoosh.play()
                }
            } else {
                deathLockTime -= deltaTime
            }
            if (dipToBlack.isFinished) {
                scene = GameScene(renderer, resources, highScore)
            }
        }
    }
}
