package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.engine.*
import pl.gieted.flappy_bird.engine.collisions.Collidable
import pl.gieted.flappy_bird.engine.collisions.colliders.Collider
import pl.gieted.flappy_bird.engine.collisions.colliders.MultiCollider
import pl.gieted.flappy_bird.engine.collisions.colliders.RectangularCollider
import processing.core.PImage


class Pipe(renderer: Renderer, position: Vector2 = Vector2.zero, texture: PImage) :
    Object(renderer, position, zIndex = -2), Measurable, Collidable {

    companion object {
        const val gapHeight = 375
    }

    private val upperPipe = Sprite(renderer, position, rotation = 180.0, texture = texture)
    private val lowerPipe = Sprite(renderer, position, texture = texture)

    override val size: Vector2
        get() = Vector2(upperPipe.texture.width, upperPipe.texture.height * 2 - gapHeight)

    override var position: Vector2
        get() = super.position
        set(value) {
            super.position = value
            upperPipe.position = value + Vector2(0, gapHeight / 2 + size.y / 4)
            lowerPipe.position = value - Vector2(0, gapHeight / 2 + size.y / 4)
        }

    override fun setup() {
        addChild(upperPipe)
        addChild(lowerPipe)
    }

    override val collider: Collider
        get() = MultiCollider(
            setOf(
                RectangularCollider(upperPipe.position, upperPipe.size),
                RectangularCollider(lowerPipe.position, lowerPipe.size)
            )
        )
}
