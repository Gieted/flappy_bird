package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.Renderer

open class Curtain(renderer: Renderer, color: Color = Color.black) :
    Rectangle(renderer, size = Vector2.screen + Vector2.defaultSize, zIndex = 1000, color = color) {

    val fadeInFinished: Boolean
        get() = opacity == 0.0
    
    val fadeOutFinished: Boolean
      get() = opacity == 1.0

    fun fadeIn(speed: Double, deltaTime: Int) {
        if (opacity > 0.0) {
            opacity -= speed * deltaTime
        }
    }

    fun fadeOut(speed: Double, deltaTime: Int) {
        if (opacity < 1.0) {
            opacity += speed * deltaTime
        }
    }

    override fun draw() {
        with(renderer) { 
            position = camera.position
        }
        super.draw()
    }
}
