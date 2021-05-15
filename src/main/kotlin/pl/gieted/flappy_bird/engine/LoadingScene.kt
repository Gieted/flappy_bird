package pl.gieted.flappy_bird.engine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.gieted.flappy_bird.Renderer

abstract class LoadingScene<T>(renderer: Renderer) : Scene(renderer) {
    protected abstract val backgroundImagePath: String
    protected abstract val resourceLoader: ResourceLoader

    private val curtain = Curtain(renderer)
    private val progressBar = ProgressBar(renderer)
    private var isBackgroundPresent = false
    private var resources: T? = null
    private var sceneExitTime: Int? = null
    private var loadingStarted = false

    private fun loadBackgroundImage() {
        lifecycleScope.launch(Dispatchers.IO) {
            with(renderer) {
                val backgroundImage = loadImage(backgroundImagePath)
                addObject(Background(renderer, backgroundImage))
                isBackgroundPresent = true
            }
        }
    }

    abstract suspend fun loadResources(): T

    abstract fun getNextScene(resources: T): Scene

    override fun setup() {
        super.setup()
        addObject(curtain)
        addObject(progressBar)
        loadBackgroundImage()
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            if (isBackgroundPresent && !loadingStarted) {
                curtain.fadeIn(0.0025, deltaTime)
            }

            progressBar.progressPercentage = resourceLoader.progressPercentage

            if (curtain.fadeInFinished && !loadingStarted) {
                loadingStarted = true
                lifecycleScope.launch(Dispatchers.IO) {
                    resources = loadResources()
                    sceneExitTime = millis() + 500
                }
            }

            val sceneExitTime = sceneExitTime
            if (sceneExitTime != null && millis() > sceneExitTime) {
                curtain.fadeOut(0.001, deltaTime)
                if (curtain.fadeOutFinished) {
                    scene = getNextScene(resources!!)
                }
            }
        }
    }
}
