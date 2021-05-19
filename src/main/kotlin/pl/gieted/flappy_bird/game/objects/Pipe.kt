package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.engine.*
import processing.core.PImage


class Pipe(renderer: Renderer, position: Vector2 = Vector2.zero, texture: PImage) :
    Object(renderer, position, zIndex = -2), Measurable {

    companion object {
        const val gapHeight = 350
    }

    private val upperPipe = Sprite(renderer, rotation = 180.0, texture = texture)
    private val lowerPipe = Sprite(renderer, texture = texture)

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

        position = position
    }
}
