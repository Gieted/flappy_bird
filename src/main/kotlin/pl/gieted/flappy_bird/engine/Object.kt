package pl.gieted.flappy_bird.engine

abstract class Object(
    renderer: Renderer,
    open var position: Vector2 = Vector2.zero,
    open var zIndex: Int = 0,
) : LifecycleElement(renderer) {
    var detached = false
        private set

    private val mutableChildren = mutableListOf<Object>()

    val children: List<Object> = mutableChildren

    protected fun detach() {
        detached = true
    }

    protected fun addChild(child: Object) {
        child.setup()
        mutableChildren.add(child)
    }

    protected fun removeChild(child: Object) {
        child.destroy()
        mutableChildren.remove(child)
    }

    override fun draw() {
        mutableChildren.sortedBy { it.zIndex }.forEach {
            if (it.detached) {
                removeChild(it)
            } else {
                renderer.pushMatrix()
                it.draw()
                renderer.popMatrix()
            }
        }
    }

    override fun destroy() {
        super.destroy()
        mutableChildren.forEach { it.destroy() }
    }
}
