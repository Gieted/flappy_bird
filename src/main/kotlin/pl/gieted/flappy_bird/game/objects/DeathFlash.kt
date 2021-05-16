package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.Renderer
import pl.gieted.flappy_bird.engine.Animation
import pl.gieted.flappy_bird.engine.Color
import pl.gieted.flappy_bird.engine.FullscreenRectangle

class DeathFlash(renderer: Renderer) : FullscreenRectangle(renderer, Color.white), Animation {
    
    companion object {
        const val speed = 0.005
    }
    
    override fun setup() {
        super.setup()
        opacity = 0.0
    }

    private var direction = true

    override fun draw() {
        super.draw()
        with(renderer) {
            
            if (direction) {
                opacity += speed * deltaTime
            } else {
                opacity -= speed * deltaTime
            }

            if (opacity > 0.5) {
                direction = false
            }
        }
    }

    override val isFinished: Boolean
        get() = !direction && opacity == 0.0
}
