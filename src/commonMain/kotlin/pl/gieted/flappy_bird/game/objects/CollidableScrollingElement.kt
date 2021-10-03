package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.engine.Measurable
import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.engine.Vector2
import pl.gieted.flappy_bird.engine.collisions.Collidable
import pl.gieted.flappy_bird.engine.collisions.colliders.Collider
import pl.gieted.flappy_bird.engine.collisions.colliders.MultiCollider

class CollidableScrollingElement(
    renderer: Renderer,
    elementFactory: () -> Measurable,
    parallax: Double = 0.0,
    startXPos: Double = renderer.camera.bounds.left - Vector2.defaultSize.x * 5,
    zIndex: Int = 0,
    offset: Double = 0.0
) : ScrollingElement(renderer, elementFactory, parallax, startXPos, zIndex, offset), Collidable {

    override val collider: Collider
        get() = MultiCollider(elements.flatMap {
            if (it is Collidable) listOf(it.collider) else emptyList()
        }.toSet())
}
