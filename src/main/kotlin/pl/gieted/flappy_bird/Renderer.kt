package pl.gieted.flappy_bird

import pl.gieted.flappy_bird.engine.Camera
import pl.gieted.flappy_bird.engine.Scene
import pl.gieted.flappy_bird.scenes.FlappyBirdLoadingScene
import processing.core.PApplet

class Renderer : PApplet() {
    companion object {
        const val windowWidth = 1440
        const val windowHeight = 810
    }
    
    private var lastDrawTime: Int = 0
    var deltaTime: Int = 0
    
    var scene: Scene = FlappyBirdLoadingScene(this)
        set(value) {
            scene.destroy()
            field = value
            value.setup()
        }
    
    val camera: Camera
      get() = scene.camera

    override fun setup() {
        lastDrawTime = millis()
        scene.setup()
    }

    override fun draw() {
        background(0)
        deltaTime = millis() - lastDrawTime
        lastDrawTime = millis()
        scene.draw()
    }

    override fun settings() {
        size(windowWidth, windowHeight, P2D)
    }
}
