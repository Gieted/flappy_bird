package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.Renderer
import processing.core.PImage

open class Sprite(
    renderer: Renderer,
    position: Vector2 = Vector2.zero,
    zIndex: Int = 0,
    var rotation: Double = 0.0,
    var texture: PImage,
    opacity: Double = 1.0
) : Object(renderer, position, zIndex) {
    var opacity: Double = limit(opacity, 0.0, 1.0)
        set(value) {
            field = limit(value, 0.0, 1.0)
        }

    val size: Vector2
        get() = Vector2(texture.width, texture.height)

    val bounds: Bounds
        get() = Bounds(
            position.x - size.x / 2,
            position.x + size.x / 2,
            position.y - size.y / 2,
            position.y + size.y / 2
        )

    override fun setup() {
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            tint(255, (255 * opacity).toFloat())
            rotateAround(position, rotation)
            image(texture, (position.x - size.x / 2).toFloat(), (-position.y - size.y / 2).toFloat())
        }
    }
}
