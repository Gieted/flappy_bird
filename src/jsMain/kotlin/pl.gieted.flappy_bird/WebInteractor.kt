package pl.gieted.flappy_bird

import pl.gieted.flappy_bird.engine.Interactor

class WebInteractor(private val framerateManager: FramerateManager) : Interactor {
    
    override fun reportFrameRate(frameRate: Int) {
        framerateManager.currentFps = frameRate
    }
}
