package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.engine.*

class DeathFlash(renderer: Renderer) : Object(renderer, zIndex = 10000000), Animation {
    private val rectangle = FullscreenRectangle().apply { 
        opacity = 0.0
    }
    
    companion object {
        const val speed = 0.005
    }
    
    override fun setup() {
        
    }

    private var direction = true

    override fun draw() {
        rectangle.draw(renderer, renderer.deltaTime)
        with(renderer) {
            if (direction) {
                rectangle.opacity += speed * deltaTime
            } else {
                rectangle.opacity -= speed * deltaTime
            }

            if (rectangle.opacity > 0.5) {
                direction = false
            }
        }
    }

    override val isFinished: Boolean
        get() = !direction && rectangle.opacity == 0.0
}
