package pl.gieted.flappy_bird

import pl.gieted.flappy_bird.engine.Renderer

fun main() {
    val framerateManager = FramerateManager()
    val interactor = WebInteractor(framerateManager)
    Renderer(interactor = interactor).start()
    framerateManager.start()
}
