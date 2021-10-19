package pl.gieted.flappy_bird.game

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.engine.*
import pl.gieted.flappy_bird.game.objects.PulsingLogo

class LoadingScene(renderer: Renderer, private val highScoreRepository: HighScoreRepository) : Scene(renderer) {

    private companion object {
        const val backgroundImagePath = "loading.png"
        const val transitionsSpeed = 2.0
        const val sceneExitOffset = 1000
    }

    private val resourceLoader = FlappyBirdResourceLoader(renderer)
    private val progressBar = ProgressBar(renderer, color = Color(248, 183, 51))
    private var resources: Resources? = null
    private var highScore: Int? = null
    private var sceneExitTime: Int? = null
    private val dipToBlack = DipToBlack(renderer, transitionsSpeed)

    private suspend fun loadBackgroundImage() {
        val texture = resourceLoader.loadImage(backgroundImagePath)
        addObject(PulsingLogo(renderer, texture = texture))
    }

    private suspend fun loadResources() = coroutineScope {
        with(renderer) {
            val deferredResources = async { resourceLoader.loadResources() }
            val deferredHighScore = async { highScoreRepository.loadHighScore() }

            resources = deferredResources.await()
            highScore = deferredHighScore.await()
            sceneExitTime = millis() + sceneExitOffset
        }
    }

    override fun setup() {
        super.setup()
        addObject(progressBar)
        lifecycleScope.launch {
            loadBackgroundImage()
            loadResources()
        }
    }

    override fun draw() {
        with(renderer) {
            background(108f, 200f, 81f)
            progressBar.progressPercentage = resourceLoader.progressPercentage

            val sceneExitTime = sceneExitTime
            if (sceneExitTime != null && millis() > sceneExitTime) {
                once("dipToBlack") {
                    addObject(dipToBlack)
                }
                if (dipToBlack.isFinished) {
                    scene = GameScene(renderer, resources!!, highScore!!, highScoreRepository)
                }
            }
        }
        super.draw()
    }
}
