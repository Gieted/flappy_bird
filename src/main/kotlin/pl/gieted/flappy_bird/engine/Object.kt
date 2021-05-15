package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.Renderer

abstract class Object(
    renderer: Renderer,
    open var position: Vector2 = Vector2.zero,
    open var size: Vector2 = Vector2.defaultSize,
    open var rotation: Double = 0.0,
    open var zIndex: Int = 0,
) : LifecycleElement(renderer) {
    private val children = mutableListOf<Object>()

    fun addChild(child: Object) {
        child.setup()
        children.add(child)
    }

    fun removeChild(child: Object) {
        child.destroy()
        children.remove(child)
    }

    override fun draw() {
        children.sortedBy { it.zIndex }.forEach { it.draw() }
    }

    override fun destroy() {
        super.destroy()
        children.forEach { it.destroy() }
    }
}
