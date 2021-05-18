package pl.gieted.flappy_bird.engine

abstract class Scene(renderer: Renderer) : LifecycleElement(renderer) {
    open val camera: Camera = Camera(renderer)

    private val mutableObjects = mutableListOf<Object>()
    
    val objects: List<Object> = mutableObjects

    fun addObject(theObject: Object) {
        mutableObjects.add(theObject)
        theObject.setup()
    }

    fun removeObject(theObject: Object) {
        theObject.destroy()
        mutableObjects.remove(theObject)
    }

    override fun setup() {
        camera.setup()
    }

    override fun draw() {
        camera.draw()
        mutableObjects.sortedBy { it.zIndex }.forEach {
            if (it.detached) {
                removeObject(it)
            } else {
                renderer.pushMatrix()
                it.draw()
                renderer.popMatrix()
            }
        }
    }

    override fun destroy() {
        super.destroy()
        camera.destroy()
        mutableObjects.forEach { it.destroy() }
    }
}
