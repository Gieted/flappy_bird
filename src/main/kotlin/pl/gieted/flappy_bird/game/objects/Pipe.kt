package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.engine.Object
import pl.gieted.flappy_bird.engine.Sprite
import pl.gieted.flappy_bird.engine.Vector2
import processing.core.PImage


class Pipe(renderer: Renderer, position: Vector2 = Vector2.zero, texture: PImage) :
    Object(renderer, position, zIndex = -2) {

    companion object {
        const val gapHeight = 415
    }

    private val upperPipe = Sprite(renderer, position - Vector2(0, gapHeight), rotation = 180.0, texture = texture)
    private val lowerPipe = Sprite(renderer, position + Vector2(0, gapHeight), texture = texture)

    override fun setup() {
        addChild(upperPipe)
        addChild(lowerPipe)
    }
}
