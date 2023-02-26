package pl.gieted.flappy_bird.engine

abstract class RenderObject {
    var shouldBeRemoved = false
        private set

    open fun setup() {
    }

    abstract fun draw(renderer: Renderer, deltaTime: Int)

    open fun destroy() {
        shouldBeRemoved = true
    }
}
