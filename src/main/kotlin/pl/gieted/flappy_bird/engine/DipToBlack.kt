package pl.gieted.flappy_bird.engine

open class DipToBlack(renderer: Renderer, private val speed: Double = 1.0) :
    FullscreenRectangle(renderer, color = Color.black, opacity = 0.0), Animation {
    
    override fun draw() {
        super.draw()
        with(renderer) {
            opacity += speed / 500 * deltaTime
        }
    }

    override val isFinished: Boolean
        get() = opacity == 1.0
}
