package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.engine.*

class GameOver(renderer: Renderer, texture: Image) : Sprite(renderer, Vector2(0, yPosition), texture = texture, scale = 1.5),
    Animation {

    companion object {
        const val yPosition = 200.0
        const val maxYPosition = yPosition + 5
        const val animationSpeed = 0.05
    }

    private var animationDirection = 1

    override fun draw() {
        with(renderer) {
            if (animationDirection == 1 && position.y > maxYPosition) {
                animationDirection = -1
            }
            if (animationDirection == -1 && position.y < yPosition) {
                animationDirection = 0
            }
            position += Vector2(0, animationSpeed * deltaTime * animationDirection)
            position = Vector2(camera.position.x, position.y)
        }
        super.draw()
    }

    override val isFinished: Boolean
        get() = animationDirection == 0
}
