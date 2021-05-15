package pl.gieted.flappy_bird.scenes.objects

import pl.gieted.flappy_bird.Renderer
import pl.gieted.flappy_bird.engine.Object
import pl.gieted.flappy_bird.engine.Sprite
import pl.gieted.flappy_bird.engine.Vector2
import processing.core.PImage


class Pipe(renderer: Renderer, position: Vector2 = Vector2.zero, private val texture: PImage) :
    Object(renderer, position, zIndex = -2) {
    private val upperPipe = Sprite(renderer, position - Vector2(0, gapHeight), rotation = 180.0, texture = texture)
    private val lowerPipe = Sprite(renderer, position + Vector2(0, gapHeight), texture = texture)

    val isOnScreen: Boolean
        get() = upperPipe.isOnScreen
    
    companion object {
        const val gapHeight = 415
    }

    override fun setup() {
        addChild(upperPipe)
        addChild(lowerPipe)
    }
}
