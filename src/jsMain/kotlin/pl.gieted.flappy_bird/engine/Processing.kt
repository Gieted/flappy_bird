package pl.gieted.flappy_bird.engine

import kotlinx.browser.window

actual open class Processing actual constructor() {
    
    
    actual fun loadImage(path: String): Image = Image(pApplet.loadImage(path))

    actual var height: Int
        get() = window.innerHeight
        set(value) {}
    
    actual var width: Int
        get() = window.innerWidth
        set(value) {}

    actual fun background(image: Image) = pApplet.background(image)

    actual fun background(code: Int) {
    }

    actual fun translate(x: Float, y: Float) {
    }

    actual fun rotate(value: Float) {
    }

    actual fun millis(): Int {
        TODO("Not yet implemented")
    }

    actual fun scale(value: Float) {
    }

    actual var mousePressed: Boolean
        get() = TODO("Not yet implemented")
        set(value) {}
    actual var displayHeight: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    actual var displayWidth: Int
        get() = TODO("Not yet implemented")
        set(value) {}

    actual fun size(x: Int, y: Int, renderer: String) {
    }

    actual open fun setup() {
    }

    actual open fun draw() {
    }

    actual open fun settings() {
    }

}
