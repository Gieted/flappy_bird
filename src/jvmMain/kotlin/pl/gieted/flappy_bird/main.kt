package pl.gieted.flappy_bird

import pl.gieted.flappy_bird.engine.Renderer
import processing.core.PApplet

fun main(args: Array<String>) {
    PApplet.runSketch(arrayOf("Flappy Bird") + args, Renderer().pApplet)
}
