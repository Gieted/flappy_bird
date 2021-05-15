package pl.gieted.flappy_bird.scenes.objects

import pl.gieted.flappy_bird.Renderer
import pl.gieted.flappy_bird.engine.Object
import pl.gieted.flappy_bird.engine.Sprite
import pl.gieted.flappy_bird.engine.Vector2
import processing.core.PImage

class ScrollingBackground(
    renderer: Renderer,
    private val texture: PImage,
    private val fullScreen: Boolean = true,
    private val parallax: Double = 0.0,
    zIndex: Int = -100
) : Object(renderer, zIndex = zIndex) {
    private val backgrounds = mutableListOf<Sprite>()

    var paused = false

    companion object {
        private const val backgroundOffset = 1
    }

    private fun addBackground(background: Sprite) {
        addChild(background)
        backgrounds.add((background))
    }

    private val backgroundWidth = texture.width

    override fun setup() {
        for (i in 0..(Renderer.windowWidth / backgroundWidth) + 2) {
            val background = Sprite(
                renderer,
                Vector2(
                    -Vector2.center.x + (i * (backgroundWidth - backgroundOffset)),
                    if (fullScreen) 0.0 else Vector2.center.y
                ),
                texture = texture
            )
            addBackground(background)
        }
    }

    override fun draw() {
        for (background in backgrounds) {
            if (!background.isOnScreen) {
                background.position = backgrounds.last().position + Vector2(backgroundWidth - backgroundOffset, 0)
                backgrounds.sortBy { it.position.x }
                break
            } else {
                break
            }
        }

        if (!paused) {
            backgrounds.forEach { it.position -= Vector2(parallax, 0) }
        }
        super.draw()
    }
}
