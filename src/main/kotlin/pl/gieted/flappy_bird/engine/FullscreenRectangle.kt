package pl.gieted.flappy_bird.engine

open class FullscreenRectangle(renderer: Renderer, color: Color = Color.default, opacity: Double = 1.0) :
    Rectangle(renderer, zIndex = 1000, size = Vector2.screen + Vector2.defaultSize, color = color, opacity = opacity) {

    override fun draw() {
        super.draw()
        with(renderer) {
            position = camera.position
        }
    }
}
