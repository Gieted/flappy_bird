package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.engine.collisions.Collidable
import pl.gieted.flappy_bird.engine.collisions.CollisionListener
import pl.gieted.flappy_bird.engine.collisions.colliders.Collider

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
                renderer.noTint()
                renderer.noFill()
            }
        }

        objects.filter { it is CollisionListener }.forEach { collisionListener ->
            objects.filter { it != collisionListener && it is Collidable }.forEach { collidable ->
                if (Collider.areColliding(
                        (collisionListener as CollisionListener).collider,
                        (collidable as Collidable).collider
                    )
                ) {
                    collisionListener.onCollision(collidable)
                }
            }
        }
    }

    override fun destroy() {
        super.destroy()
        camera.destroy()
        mutableObjects.forEach { it.destroy() }
    }
}
