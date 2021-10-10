package pl.gieted.flappy_bird.game

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.engine.*

class LoadingScene(renderer: Renderer) : Scene(renderer) {

    private companion object {
        const val backgroundImagePath = "loading.png"
        const val transitionsSpeed = 2.0
        const val sceneExitOffset = 1000
    }

    private val resourceLoader = FlappyBirdResourceLoader(renderer)
    private val progressBar = ProgressBar(renderer)
    private var resources: Resources? = null
    private var highScore: Int? = null
    private var sceneExitTime: Int? = null
    private val dipFromBlack = DipFromBlack(renderer, transitionsSpeed, true)
    private val dipToBlack = DipToBlack(renderer, transitionsSpeed)
    private val highScoreRepository = HighScoreRepository()

    private fun loadBackgroundImage() {
        with(renderer) {
            lifecycleScope.launch {
                val backgroundImage = resourceLoader.loadImage(backgroundImagePath)
                if (backgroundImage.width != width) {
                    backgroundImage.resize(width, height)
                }
                addObject(Background(renderer, backgroundImage))
                dipFromBlack.start()
            }
        }
    }

    private fun loadResources() {
        with(renderer) {
            lifecycleScope.launch {
                val deferredResources = async { resourceLoader.loadResources() }
                val deferredHighScore = async { highScoreRepository.loadHighScore() }

                resources = deferredResources.await()
                highScore = deferredHighScore.await()
                sceneExitTime = millis() + sceneExitOffset
            }
        }
    }

    override fun setup() {
        super.setup()
        addObject(progressBar)
        addObject(dipFromBlack)
        loadBackgroundImage()
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            progressBar.progressPercentage = resourceLoader.progressPercentage

            if (dipFromBlack.isFinished) {
                once("loadResources") {
                    loadResources()
                }
            }

            val sceneExitTime = sceneExitTime
            if (sceneExitTime != null && millis() > sceneExitTime) {
                once("dipToBlack") {
                    addObject(dipToBlack)
                }
                if (dipToBlack.isFinished) {
                    scene = GameScene(renderer, resources!!, highScore!!)
                }
            }
        }
    }
}
