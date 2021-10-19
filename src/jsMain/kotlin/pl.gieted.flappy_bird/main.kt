package pl.gieted.flappy_bird

import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.game.HighScoreRepository

fun main() {
    val framerateManager = FramerateManager()
    val interactor = WebInteractor(framerateManager)
    Renderer(interactor = interactor, highScoreRepository = HighScoreRepository()).start()
    framerateManager.start()
}
