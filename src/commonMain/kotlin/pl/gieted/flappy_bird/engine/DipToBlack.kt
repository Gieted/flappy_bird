package pl.gieted.flappy_bird.engine

open class DipToBlack(renderer: Renderer, private val speed: Double = 1.0) :
    Object(renderer, zIndex = 1000), Animation {

    private val rectangle = FullscreenRectangle().apply { 
        opacity = 0.0
        color = Color.black
    }
    
    override fun setup() {

    }
    
    override fun draw() {
        super.draw()
        with(renderer) {
            rectangle.opacity += speed / 500 * deltaTime
            rectangle.draw(renderer, deltaTime)
        }
    }

    override val isFinished: Boolean
        get() = rectangle.opacity == 1.0
}
