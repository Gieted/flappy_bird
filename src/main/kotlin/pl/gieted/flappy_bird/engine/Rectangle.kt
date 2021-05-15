package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.Renderer

open class Rectangle(
    renderer: Renderer,
    position: Vector2 = Vector2.zero,
    size: Vector2 = Vector2.defaultSize,
    rotation: Double = 0.0,
    zIndex: Int = 0,
    var color: Color = Color.white,
    var strokeColor: Color? = null,
    var strokeWidth: Double = 1.0
) : Object(renderer, position, size, rotation, zIndex) {
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

    val isOnScreen: Boolean
        get() = isOnScreen(renderer, position, size)
    
    override fun setup() {

    }

    override fun draw() {
        super.draw()
        with(renderer) {
            pushMatrix()
            fill(color.r.toFloat(), color.g.toFloat(), color.b.toFloat(), opacity.toFloat() * 255)
            rotateAroundCenter(position, rotation)
            val outlineColor = strokeColor
            if (outlineColor != null) {
                strokeWeight(strokeWidth.toFloat())
                stroke(outlineColor.r.toFloat(), outlineColor.g.toFloat(), outlineColor.b.toFloat())
            } else {
                stroke(0, 0f)
            }
            rect((position.x - size.x / 2).toFloat(), (position.y - size.y / 2).toFloat(), size.x.toFloat(), size.y.toFloat())
            popMatrix()
        }
    }
}
