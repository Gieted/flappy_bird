package pl.gieted.flappy_bird.scenes

import pl.gieted.flappy_bird.Renderer
import pl.gieted.flappy_bird.engine.LoadingScene

class FlappyBirdLoadingScene(renderer: Renderer) : LoadingScene<FlappyBirdResources>(renderer) {
    override val backgroundImagePath: String = "images/loading.png"
    override val resourceLoader: FlappyBirdResourceLoader = FlappyBirdResourceLoader(renderer)
    
    override suspend fun loadResources(): FlappyBirdResources = resourceLoader.loadResources()

    override fun getNextScene(resources: FlappyBirdResources) = GameScene(renderer, resources)
}
