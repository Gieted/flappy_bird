package pl.gieted.flappy_bird.engine

import processing.core.PImage

class Background(renderer: Renderer, private val texture: PImage) : Object(renderer, zIndex = -100) {
    
    override fun setup() {
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            if (texture.width == width && texture.height == height) {
                background(texture)
            }
        }
    }
}
