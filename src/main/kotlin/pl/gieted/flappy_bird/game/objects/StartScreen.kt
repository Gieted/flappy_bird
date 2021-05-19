package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.engine.Sprite
import pl.gieted.flappy_bird.engine.Vector2
import processing.core.PImage

class StartScreen(renderer: Renderer, texture: PImage) :
    Sprite(renderer, Vector2(0, yPosition), zIndex = 10, texture = texture) {

    private companion object {
        const val yPosition = 75
    }

    private var fadingOut = false

    fun fadeOut() {
        fadingOut = true
    }

    override fun draw() {
        with(renderer) {
            position = Vector2(camera.position.x, yPosition)

            if (fadingOut) {
                opacity -= 0.005 * deltaTime
            }
            if (opacity == 0.0) {
                detach()
            }
        }
        super.draw()
    }
}
