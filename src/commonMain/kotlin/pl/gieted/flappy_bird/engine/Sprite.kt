package pl.gieted.flappy_bird.engine

open class Sprite(
    renderer: Renderer,
    position: Vector2 = Vector2.zero,
    zIndex: Int = 0,
    var rotation: Double = 0.0,
    var texture: Image,
    opacity: Double = 1.0,
    var scale: Double = 1.0
) : Object(renderer, position, zIndex), Measurable {
    var opacity: Double = limit(opacity, 0.0, 1.0)
        set(value) {
            field = limit(value, 0.0, 1.0)
        }

    override val size: Vector2
        get() = Vector2(texture.width * scale, texture.height * scale)

    override fun setup() {
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            rotateAround(position, rotation)
            if (opacity != 1.0) {
                tint(255, (255 * opacity).toFloat())
            }
            if (scale == 1.0) {
                image(texture, (position.x - size.x / 2).toFloat(), (-position.y - size.y / 2).toFloat())
            } else {
                image(
                    texture,
                    (position.x - size.x / 2).toFloat(),
                    (-position.y - size.y / 2).toFloat(),
                    texture.width * scale.toFloat(),
                    texture.height * scale.toFloat()
                )
            }
        }
    }
}
