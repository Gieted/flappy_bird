package pl.gieted.flappy_bird

import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.game.HighScoreRepository
import processing.opengl.PJOGL.setIcon

fun main() {
    val renderer = Renderer(highScoreRepository = HighScoreRepository()) {
        pApplet.surface.apply {
            setTitle("Flappy Bird")
            setResizable(true)
        }
    }
    setIcon(*(1..5).map { "favicons/favicon-$it.png" }.toTypedArray())
    renderer.start()
}
