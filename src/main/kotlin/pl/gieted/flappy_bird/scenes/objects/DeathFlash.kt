package pl.gieted.flappy_bird.scenes.objects

import pl.gieted.flappy_bird.Renderer
import pl.gieted.flappy_bird.engine.Color
import pl.gieted.flappy_bird.engine.Curtain

class DeathFlash(renderer: Renderer) : Curtain(renderer, Color.white) {
    override fun setup() {
        super.setup()
        opacity = 0.0
    }

    private var deathFlashIn = true

    override fun draw() {
        super.draw()
        with(renderer) {
            val flashSpeed = 0.005
            if (deathFlashIn) {
                opacity += flashSpeed * deltaTime
            } else {
                opacity -= flashSpeed * deltaTime
            }

            if (opacity > 0.5) {
                deathFlashIn = false
            }
        }
    }
}
