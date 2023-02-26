package pl.gieted.flappy_bird.engine

open class FullscreenRectangle : RenderObject() {
    private val rectangle = Rectangle()
    var color
        get() = rectangle.color
        set(value) {
            rectangle.color = value
        }

    var opacity
        get() = rectangle.opacity
        set(value) {
            rectangle.opacity = value
        }

    override fun draw(renderer: Renderer, deltaTime: Int) {
        rectangle.size = Vector2(renderer.width, Renderer.defaultHeight) + Vector2.defaultSize
        rectangle.position = renderer.camera.position
        rectangle.draw(renderer, deltaTime)
    }
}
