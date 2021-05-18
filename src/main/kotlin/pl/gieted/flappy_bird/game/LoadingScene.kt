package pl.gieted.flappy_bird.game

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
    private var sceneExitTime: Int? = null
    private val dipFromBlack = DipFromBlack(renderer, transitionsSpeed, true)
    private val dipToBlack = DipToBlack(renderer, transitionsSpeed)

    private fun loadBackgroundImage() {
        with(renderer) {
            lifecycleScope.launch {
                val backgroundImage = resourceLoader.loadImage(backgroundImagePath)
                addObject(Background(renderer, backgroundImage))
                dipFromBlack.start()
            }

            lifecycleScope.launch {
                resources = resourceLoader.loadResources()
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

            val sceneExitTime = sceneExitTime
            if (sceneExitTime != null && millis() > sceneExitTime) {
                once("dipToBlack") {
                    addObject(dipToBlack)
                }
                if (dipToBlack.isFinished) {
                    scene = GameScene(renderer, resources!!)
                }
            }
        }
    }
}
