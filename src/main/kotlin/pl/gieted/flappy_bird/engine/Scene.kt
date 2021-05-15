package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.Renderer

abstract class Scene(renderer: Renderer) : LifecycleElement(renderer) {
    open val camera: Camera = Camera(renderer)

    protected open val objects = mutableListOf<Object>()

    fun addObject(theObject: Object) {
        objects.add(theObject)
        theObject.setup()
    }

    fun removeObject(theObject: Object) {
        theObject.destroy()
        objects.remove(theObject)
    }

    override fun setup() {
        camera.setup()
    }

    override fun draw() {
        camera.draw()
        objects.sortedBy { it.zIndex }.forEach {
            it.draw()
        }
    }

    override fun destroy() {
        super.destroy()
        camera.destroy()
        objects.forEach { it.destroy() }
    }
}
