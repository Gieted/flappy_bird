package pl.gieted.flappy_bird.engine

import processing.core.PApplet

class PAppletWrapper(var processing: Processing) : PApplet() {
    
    override fun setup() {
        processing.setup()
    }

    override fun draw() {
        processing.draw()
    }

    override fun settings() {
        processing.settings()
    }
}
