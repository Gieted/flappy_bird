package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.Renderer

open class DipFromBlack(renderer: Renderer, private val speed: Double = 1.0, lazy: Boolean = false) : FullscreenRectangle(renderer, color = Color.black), Animation {
    
    private var started = !lazy

    fun start() {
        started = true
    }
    
    override fun draw() {
        super.draw()
        with(renderer) {
            if (started) {
                opacity -= speed / 500 * deltaTime
                if (isFinished) {
                    detach()
                }
            }
        }
    }

    override val isFinished: Boolean
        get() = opacity == 0.0
}
