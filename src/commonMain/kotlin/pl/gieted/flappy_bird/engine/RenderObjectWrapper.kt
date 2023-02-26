package pl.gieted.flappy_bird.engine

class RenderObjectWrapper(renderer: Renderer, private val renderObject: RenderObject) : Object(renderer) {
    override fun setup() {
        renderObject.setup()
    }

    override fun draw() {
        super.draw()
        renderObject.draw(renderer, renderer.deltaTime)
    }

    override fun destroy() {
        super.destroy()
        renderObject.destroy()
    }
}
