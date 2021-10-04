package pl.gieted.flappy_bird

import pl.gieted.flappy_bird.engine.Renderer
import processing.opengl.PJOGL.setIcon

fun main() {
    val renderer = Renderer {
        pApplet.surface.apply {
            setTitle("Flappy Bird")
            setResizable(false)
        }
    }
    setIcon(*(1..5).map { "favicons/favicon-$it.png" }.toTypedArray())
    renderer.start()
}
