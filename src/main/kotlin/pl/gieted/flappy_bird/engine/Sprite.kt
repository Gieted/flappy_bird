package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.Renderer
import processing.core.PImage

open class Sprite(
    renderer: Renderer,
    position: Vector2 = Vector2.zero,
    size: Vector2? = null,
    rotation: Double = 0.0,
    zIndex: Int = 0,
    texture: PImage,
) : Object(renderer, position, size ?: Vector2(texture.width, texture.height), rotation, zIndex) {
    var opacity: Double = 1.0
        set(value) {
            var boundValue = value
            if (value > 1.0) {
                boundValue = 1.0
            }
            if (value < 0.0) {
                boundValue = 0.0
            }
            field = boundValue
        }

    var texture: PImage = texture
        set(value) {
            size = Vector2(value.width, value.height)
            field = value
        }

    val isOnScreen: Boolean
        get() = isOnScreen(renderer, position, size)

    override fun setup() {
        this.size = size
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            pushMatrix()
            tint(255, (255 * opacity).toFloat())
            rotateAroundCenter(position, rotation)
            image(texture, (position.x - size.x / 2).toFloat(), (position.y - size.y / 2).toFloat())
            popMatrix()
        }
    }
}
