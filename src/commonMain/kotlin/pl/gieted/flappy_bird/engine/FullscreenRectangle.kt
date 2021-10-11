package pl.gieted.flappy_bird.engine

open class FullscreenRectangle(renderer: Renderer, color: Color = Color.default, opacity: Double = 1.0) :
    Rectangle(renderer, zIndex = 2000, size = Vector2.screen + Vector2.defaultSize, color = color, opacity = opacity) {

    override fun draw() {
        with(renderer) {
            size = Vector2(width, Renderer.defaultHeight) + Vector2.defaultSize
            position = camera.position
        }
        super.draw()
    }
}
