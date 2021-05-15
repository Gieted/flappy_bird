package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.Renderer

class Camera(
    renderer: Renderer,
    position: Vector2 = Vector2.zero,
    size: Vector2 = Vector2.one,
    rotation: Double = 0.0
) : Object(renderer, position, size, rotation, -1_000_000) {
    override fun setup() {
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            val position = Vector2.center - position - Vector2(4, 0)
            translate(position.x.toFloat(), position.y.toFloat())
            scale(size.x.toFloat(), size.y.toFloat())
            rotate(rotation.toFloat())
        }
    }
}
