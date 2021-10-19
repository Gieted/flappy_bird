package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.engine.Image
import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.engine.Sprite

class PulsingLogo(renderer: Renderer, texture: Image) : Sprite(renderer, texture = texture, opacity = 0.0) {

    companion object {
        private const val minOpacity = 0.6
        private const val topHoldTime = 1000
    }

    private var direction = 1
    private var initialDissolveCompleted = false
    private var topHoldCounter = 0

    override fun draw() {
        with(renderer) {
            scale = 0.00025 * width
            opacity += direction * deltaTime.toDouble() / if (initialDissolveCompleted) 2500 else 1000

            if (direction == 1 && opacity == 1.0) {
                initialDissolveCompleted = true
                topHoldCounter += deltaTime
                
                if (topHoldCounter > topHoldTime) {
                    topHoldCounter = 0
                    direction = -1
                }
            }
            if (direction == -1 && opacity < minOpacity) {
                opacity = minOpacity
                direction = 1
            }
        }
        super.draw()
    }
}
