package pl.gieted.flappy_bird.engine

class Camera(
    renderer: Renderer,
    position: Vector2 = Vector2.zero,
) : Object(renderer, position, -1_000_000) {

    val bounds
        get() = Bounds(
            position.x - Vector2.halfScreen.x,
            position.x + Vector2.halfScreen.x,
            position.y - Vector2.halfScreen.y,
            position.y + Vector2.halfScreen.y
        )

    override fun setup() {
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            translate((Vector2.halfScreen.x - position.x).toFloat(), (Vector2.halfScreen.y + position.y).toFloat())
        }
    }
}
